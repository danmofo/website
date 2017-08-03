package com.dmoffat.website.util;

import com.dmoffat.website.model.Author;
import com.dmoffat.website.model.Post;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

/**
 * Utilities to aid with writing unit / integration tests.
 *
 * @author danielmoffat
 */
public class TestUtils {
    private static Random rng = new Random();

    public static Post createRandomPost() {
        long pseudoPostId = rng.nextLong();
        return new Post.Builder()
                .id(pseudoPostId)
                .permalink("dummy-post-" + pseudoPostId)
                .title("Dummy post #" + pseudoPostId)
                .author(new Author("Daniel Moffat"))
                .content("This is content for dummy post " + pseudoPostId)
                .build();
    }

    public static Post createRandomPostWithAuthor(String authorName) {
        Post post = createRandomPost();
        post.setAuthor(new Author(authorName));
        return post;
    }

    public static List<Post> createRandomPosts(int size) {
        List<Post> posts = Lists.newArrayListWithExpectedSize(size);

        for (int i = 0; i < size; i++) {
            posts.add(createRandomPost());
        }

        return posts;
    }

    public static List<Post> createRandomPostsWithAuthor(int size, String authorName) {
        List<Post> posts = Lists.newArrayListWithExpectedSize(size);

        for (int i = 0; i < size; i++) {
            posts.add(createRandomPostWithAuthor(authorName));
        }

        return posts;
    }
}
