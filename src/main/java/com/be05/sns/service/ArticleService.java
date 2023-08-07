package com.be05.sns.service;

import com.be05.sns.dto.Article.AUserFeedDto;
import com.be05.sns.dto.Article.UserFeedsDto;
import com.be05.sns.dto.ArticleDto;
import com.be05.sns.entity.Article;
import com.be05.sns.entity.ArticleImages;
import com.be05.sns.entity.Users;
import com.be05.sns.repository.ArticleImagesRepository;
import com.be05.sns.repository.ArticleRepository;
import com.be05.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleImagesRepository imagesRepository;
    private final UserRepository userRepository;
    private final ImageFormattingService imageFormatting;
    private final ModelMapper modelMapper;

    // 1. create feed
    public void createFeed(String title, String content,
                           List<MultipartFile> imageFiles,
                           Authentication authentication) {
        Users user = getUsers(authentication.getName());

        // 피드 선 저장
        ArticleDto articleDto = new ArticleDto().newArticle(user, title, content);
        Article article = modelMapper.map(articleDto, Article.class); // ModelConfig 참고
        articleRepository.save(article);

        // 여러 이미지 업로드 후 피드 저장
        if (imageFiles != null && !imageFiles.isEmpty()) {
            uploadImages(article, imageFiles);
            articleRepository.save(article);
        }

        // 썸네일 추가 후 저장
        ArticleImages articleImage =
                imagesRepository.findByIdAndArticleId_Id(1L, article.getId());
        if (articleImage != null) {
            article.setThumbnail(articleImage.getImageUrl());
            articleRepository.save(article);
        }
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

    // 2. writer all feed
    public Page<UserFeedsDto> readAllFeed(String userName) {
        Users user = getUsers(userName);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        Page<Article> articles = articleRepository
                .findAllByUserId_Username(user.getUsername(), pageable);

        return articles.map(UserFeedsDto::fromAllFeed);
    }

    // 3. read a feed
    public AUserFeedDto read(Long articleId) {
        Optional<Article> feed = articleRepository.findById(articleId);
        if (feed.isPresent()) return AUserFeedDto.fromFeedInfo(feed.get());
        else throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    // 유저 Entity 불러오기
    private Users getUsers(String userName) {
        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
    }
}