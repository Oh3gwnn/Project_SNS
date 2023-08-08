package com.be05.sns.service;

import com.be05.sns.dto.feed.AFeedDto;
import com.be05.sns.dto.ArticleDto;
import com.be05.sns.dto.feed.UserFeedsDto;
import com.be05.sns.dto.comment.readCommentDto;
import com.be05.sns.entity.*;
import com.be05.sns.repository.ArticleImagesRepository;
import com.be05.sns.repository.ArticleRepository;
import com.be05.sns.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final GetObjService getObj;
    private final ModelMapper modelMapper;
    private final ImageFormattingService imageFormatting;

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final ArticleImagesRepository imagesRepository;

    // 1. create feed
    public void createFeed(String title, String content,
                           List<MultipartFile> imageFiles,
                           Authentication authentication) {
        Users user = getObj.getUser(authentication.getName());

        // 피드 먼저 저장
        ArticleDto articleDto = new ArticleDto().newArticle(user, title, content);
        Article article = modelMapper.map(articleDto, Article.class); // ModelConfig 참고
        articleRepository.save(article);

        saveImagesAndThumbnail(imageFiles, article);
    }

    // 1-1. 여러 이미지 업로드
    private void uploadImages(Article article,
                              List<MultipartFile> imageFiles) {
        String dirPath = String.format("images/article/%d/", article.getId());

        int number = 0;
        for (MultipartFile imageFile : imageFiles) {
            ArticleImages articleImages = new ArticleImages();
            String fileName = imageFormatting
                    .uploadImage(imageFile, String.valueOf(++number), dirPath);

            articleImages.setArticleId(article);
            articleImages.setImageUrl(String.format("/static/%s", fileName));
            imagesRepository.save(articleImages);
        }
    }

    // 2. writer's all feed (작성자 전체 피드)
    public Page<UserFeedsDto> readAllFeed(String userName) {
        Users user = getObj.getUser(userName);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        Page<Article> articles = articleRepository
                .findAllByUserId_Username(user.getUsername(), pageable);

        return articles.map(UserFeedsDto::fromAllFeed);
    }

    // 2-1. Follow all feed
    public Page<UserFeedsDto> readAllFollowFeed(Authentication authentication) {
        Users user = getObj.getUser(authentication.getName());
        List<UserFollows> followings = getObj.getFollow(user.getId());
        List<String> followingList = followings.stream()
                .map(userFollows -> userFollows.getFollowing().getUsername()).toList();

        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        Page<Article> articles = articleRepository
                .findAllByUserId_UsernameIn(followingList, pageable);

        return articles.map(UserFeedsDto::fromAllFeed);
    }

    // 2-2. Friend all feed
    public Page<UserFeedsDto> readAllFriendFeed(Authentication authentication) {
        Users user = getObj.getUser(authentication.getName());
        List<UserFriends> friends = getObj.getFriend(user.getId());
        List<String> friendList = friends.stream()
                .map(userFriends -> userFriends.getFromUser().getUsername()).toList();

        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        Page<Article> articles = articleRepository
                .findAllByUserId_UsernameIn(friendList, pageable);

        return articles.map(UserFeedsDto::fromAllFeed);
    }

    // 3. read a feed (피드 하나 읽어오기)
    public AFeedDto read(Long articleId) {
        Article feed = getObj.getArticle(articleId);

        // 이미지 리스트
        List<ArticleImages> images = imagesRepository.findAllByArticleId_Id(articleId);
        List<String> imageUrls = images.stream()
                .map(ArticleImages::getImageUrl).toList();

        // 댓글 리스트
        List<Comment> comments = commentRepository.findAllByArticleId_Id(articleId);
        List<readCommentDto> commentList = comments.stream()
                .map(readCommentDto::fromComment).toList();

        // 좋아요 수
        long likeCount = feed.getLikes().size();

        return AFeedDto.fromFeedInfo(feed, imageUrls, commentList, likeCount);
    }

    // 4. 피드 업데이트(제목, 내용, 이미지 수정 및 삭제)
    public void updateFeed(Long articleId, String title, String content,
                           List<String> deleteImages, List<MultipartFile> imageFiles,
                           Authentication authentication) {
        Users user = getObj.getUser(authentication.getName());
        Article preArticle = getObj.getArticle(articleId);

        validatePassword(user, preArticle); // 비밀번호 확인

        if (!title.isEmpty()) preArticle.setTitle(title);
        if (!content.isEmpty()) preArticle.setContent(content);
        if (deleteImages != null && !deleteImages.isEmpty()) {
            for (String deleteImage : deleteImages) {
                ArticleImages articleImage =
                        imagesRepository.findByImageUrlContaining(deleteImage);
                imagesRepository.delete(articleImage);
            }
        }
        articleRepository.save(preArticle);
        saveImagesAndThumbnail(imageFiles, preArticle);
    }

    // 5. 피드 삭제(삭제 시각 기록)
    public void delete(Long articleId, Authentication authentication) {
        Users user = getObj.getUser(authentication.getName());
        Article article = getObj.getArticle(articleId);

        validatePassword(user, article); // 비밀번호 확인

        article.setDeletedAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        articleRepository.save(article);
    }

    // 이미지, 썸네일 저장 메서드
    private void saveImagesAndThumbnail(List<MultipartFile> imageFiles,
                                        Article article) {
        // 여러 이미지 업로드 후 피드 저장
        if (imageFiles != null && !imageFiles.isEmpty()) {
            uploadImages(article, imageFiles);
            articleRepository.save(article);
        }

        // 썸네일 추가 후 저장
        ArticleImages firstImage =
                imagesRepository.findFirstByArticleId_IdOrderByIdAsc(article.getId());
        if (firstImage != null) {
            article.setThumbnail(firstImage.getImageUrl());
            articleRepository.save(article);
        }
    }

    // 비밀번호 검증
    private void validatePassword(Users user, Article article) {
        if (!user.getPassword().equals(article.getUserId().getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}