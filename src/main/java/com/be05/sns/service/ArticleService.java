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
        Users user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        ArticleDto articleDto = new ArticleDto().newArticle(user, title, content);
        Article article = modelMapper.map(articleDto, Article.class); // ModelConfig 참고
        articleRepository.save(article);

        if (imageFiles != null && !imageFiles.isEmpty()) {
            uploadImages(authentication, article, imageFiles);
            articleRepository.save(article);
        }
    }

    // 1-1. 여러 이미지 업로드
    private void uploadImages(Authentication authentication,
                              Article article,
                              List<MultipartFile> imageFiles) {
        String userName = authentication.getName();
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
    public Page<UserFeedsDto> readAllFeed(Long userId, Authentication authentication) {
        Users writer = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Users viewer = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        Page<Article> articles = articleRepository
                .findAllByUserId_Username(writer.getUsername(), pageable);

        return articles.map(UserFeedsDto::fromAllFeed);
    }

    // 3. read a feed
    public AUserFeedDto read(Long userId, Long articleId, Authentication authentication) {
        Optional<Article> feed = articleRepository.findByIdAndUserId_Id(userId, articleId);
        if (feed.isPresent()) return AUserFeedDto.fromFeedInfo(feed.get());
        else throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}