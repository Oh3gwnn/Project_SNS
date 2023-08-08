package com.be05.sns.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LikeId implements Serializable {
    @Column(name = "article_id")
    private long articleId;

    @Column(name = "user_id")
    private long userId;
}