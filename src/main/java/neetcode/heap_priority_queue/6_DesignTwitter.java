package neetcode.heap_priority_queue;

import java.util.*;

/**
 * NeetCode Problem 6 (Heap/Priority Queue): Design Twitter
 * 
 * Problem Description:
 * Design a simplified version of Twitter where users can post tweets, follow/unfollow another user,
 * and is able to see the 10 most recent tweets in the user's news feed.
 * 
 * Implement the Twitter class:
 * - Twitter() Initializes your twitter object.
 * - void postTweet(int userId, int tweetId) Composes a new tweet with ID tweetId by the user userId.
 *   Each call to this function will be made with a unique tweetId.
 * - List<Integer> getNewsFeed(int userId) Retrieves the 10 most recent tweet IDs in the user's news feed.
 *   Each item in the news feed must be posted by users who the user followed or by the user themself.
 *   Tweets must be ordered from most recent to least recent.
 * - void follow(int followerId, int followeeId) The user with ID followerId started following the user with ID followeeId.
 * - void unfollow(int followerId, int followeeId) The user with ID followerId started unfollowing the user with ID followeeId.
 * 
 * Examples:
 * Input:
 * ["Twitter", "postTweet", "getNewsFeed", "follow", "postTweet", "getNewsFeed", "unfollow", "getNewsFeed"]
 * [[], [1, 5], [1], [1, 2], [2, 6], [1], [1, 2], [1]]
 * 
 * Output:
 * [null, null, [5], null, null, [6, 5], null, [5]]
 * 
 * Explanation:
 * Twitter twitter = new Twitter();
 * twitter.postTweet(1, 5); // User 1 posts a new tweet (id = 5).
 * twitter.getNewsFeed(1);  // User 1's news feed should return a list with 1 tweet id -> [5]. return [5]
 * twitter.follow(1, 2);    // User 1 follows user 2.
 * twitter.postTweet(2, 6); // User 2 posts a new tweet (id = 6).
 * twitter.getNewsFeed(1);  // User 1's news feed should return a list with 2 tweet ids -> [6, 5]. Tweet id 6 should precede tweet id 5 because it is posted after tweet id 5.
 * twitter.unfollow(1, 2);  // User 1 unfollows user 2.
 * twitter.getNewsFeed(1);  // User 1's news feed should return a list with 1 tweet id -> [5], since user 1 is no longer following user 2.
 * 
 * Constraints:
 * - 1 <= userId, followerId, followeeId <= 500
 * - 0 <= tweetId <= 10^4
 * - All the tweets have unique IDs.
 * - At most 3 * 10^4 calls will be made to postTweet, getNewsFeed, follow, and unfollow.
 * 
 * Approach:
 * We'll use a combination of hash maps and a priority queue to implement this design:
 * 
 * 1. Use a hash map to store the tweets posted by each user.
 * 2. Use a hash map to store the users followed by each user.
 * 3. Use a timestamp to track the order of tweets.
 * 4. For getNewsFeed, use a max-heap (priority queue) to merge the tweets from the user and all followees.
 * 
 * Time Complexity:
 * - postTweet: O(1)
 * - getNewsFeed: O(N log N) where N is the number of tweets from the user and followees
 * - follow: O(1)
 * - unfollow: O(1)
 * 
 * Space Complexity: O(U + T) where U is the number of users and T is the number of tweets
 */
public class DesignTwitter {
    
    /**
     * Main class that implements the Twitter functionality.
     */
    static class Twitter {
        // Global timestamp to track the order of tweets
        private int timestamp;
        
        // Map of user ID to list of tweets (tweetId, timestamp)
        private Map<Integer, List<Tweet>> userTweets;
        
        // Map of user ID to set of followees
        private Map<Integer, Set<Integer>> following;
        
        /**
         * Inner class to represent a tweet with its ID and timestamp.
         */
        private static class Tweet {
            int tweetId;
            int timestamp;
            
            public Tweet(int tweetId, int timestamp) {
                this.tweetId = tweetId;
                this.timestamp = timestamp;
            }
        }
        
        /**
         * Initialize your data structure here.
         */
        public Twitter() {
            timestamp = 0;
            userTweets = new HashMap<>();
            following = new HashMap<>();
        }
        
        /**
         * Compose a new tweet.
         * 
         * @param userId The ID of the user posting the tweet
         * @param tweetId The ID of the tweet
         */
        public void postTweet(int userId, int tweetId) {
            // Ensure the user exists in our data structure
            if (!userTweets.containsKey(userId)) {
                userTweets.put(userId, new ArrayList<>());
            }
            if (!following.containsKey(userId)) {
                following.put(userId, new HashSet<>());
                // Users follow themselves to see their own tweets
                following.get(userId).add(userId);
            }
            
            // Add the tweet to the user's list of tweets
            userTweets.get(userId).add(new Tweet(tweetId, timestamp++));
        }
        
        /**
         * Retrieve the 10 most recent tweet ids in the user's news feed.
         * Each item in the news feed must be posted by users who the user followed or by the user themself.
         * Tweets must be ordered from most recent to least recent.
         * 
         * @param userId The ID of the user
         * @return List of the 10 most recent tweet IDs
         */
        public List<Integer> getNewsFeed(int userId) {
            // Ensure the user exists in our data structure
            if (!following.containsKey(userId)) {
                following.put(userId, new HashSet<>());
                following.get(userId).add(userId);
            }
            
            // Create a max-heap based on tweet timestamp
            PriorityQueue<Tweet> maxHeap = new PriorityQueue<>(
                (a, b) -> b.timestamp - a.timestamp
            );
            
            // Add tweets from the user and all followees to the heap
            Set<Integer> followees = following.get(userId);
            for (int followeeId : followees) {
                List<Tweet> tweets = userTweets.getOrDefault(followeeId, new ArrayList<>());
                for (Tweet tweet : tweets) {
                    maxHeap.offer(tweet);
                }
            }
            
            // Extract the 10 most recent tweets
            List<Integer> newsFeed = new ArrayList<>();
            int count = 0;
            while (!maxHeap.isEmpty() && count < 10) {
                newsFeed.add(maxHeap.poll().tweetId);
                count++;
            }
            
            return newsFeed;
        }
        
        /**
         * Follower follows a followee.
         * 
         * @param followerId The ID of the follower
         * @param followeeId The ID of the followee
         */
        public void follow(int followerId, int followeeId) {
            // Ensure the users exist in our data structure
            if (!following.containsKey(followerId)) {
                following.put(followerId, new HashSet<>());
                following.get(followerId).add(followerId);
            }
            if (!following.containsKey(followeeId)) {
                following.put(followeeId, new HashSet<>());
                following.get(followeeId).add(followeeId);
            }
            
            // Add the followee to the follower's set of followees
            following.get(followerId).add(followeeId);
        }
        
        /**
         * Follower unfollows a followee.
         * 
         * @param followerId The ID of the follower
         * @param followeeId The ID of the followee
         */
        public void unfollow(int followerId, int followeeId) {
            // Ensure the users exist in our data structure
            if (!following.containsKey(followerId)) {
                following.put(followerId, new HashSet<>());
                following.get(followerId).add(followerId);
                return;
            }
            
            // Users cannot unfollow themselves
            if (followerId == followeeId) {
                return;
            }
            
            // Remove the followee from the follower's set of followees
            following.get(followerId).remove(followeeId);
        }
    }
    
    /**
     * Alternative implementation using a more memory-efficient approach.
     * This implementation uses a linked list structure for tweets to avoid storing all tweets in memory.
     */
    static class TwitterOptimized {
        // Global timestamp to track the order of tweets
        private int timestamp;
        
        // Map of user ID to their most recent tweet
        private Map<Integer, Tweet> userTweets;
        
        // Map of user ID to set of followees
        private Map<Integer, Set<Integer>> following;
        
        /**
         * Inner class to represent a tweet with its ID, timestamp, and a reference to the next tweet.
         */
        private static class Tweet {
            int tweetId;
            int timestamp;
            Tweet next;
            
            public Tweet(int tweetId, int timestamp) {
                this.tweetId = tweetId;
                this.timestamp = timestamp;
                this.next = null;
            }
        }
        
        /**
         * Initialize your data structure here.
         */
        public TwitterOptimized() {
            timestamp = 0;
            userTweets = new HashMap<>();
            following = new HashMap<>();
        }
        
        /**
         * Compose a new tweet.
         * 
         * @param userId The ID of the user posting the tweet
         * @param tweetId The ID of the tweet
         */
        public void postTweet(int userId, int tweetId) {
            // Ensure the user exists in our data structure
            if (!following.containsKey(userId)) {
                following.put(userId, new HashSet<>());
                following.get(userId).add(userId);
            }
            
            // Create a new tweet
            Tweet newTweet = new Tweet(tweetId, timestamp++);
            
            // Add the tweet to the user's linked list of tweets
            newTweet.next = userTweets.getOrDefault(userId, null);
            userTweets.put(userId, newTweet);
        }
        
        /**
         * Retrieve the 10 most recent tweet ids in the user's news feed.
         * 
         * @param userId The ID of the user
         * @return List of the 10 most recent tweet IDs
         */
        public List<Integer> getNewsFeed(int userId) {
            // Ensure the user exists in our data structure
            if (!following.containsKey(userId)) {
                following.put(userId, new HashSet<>());
                following.get(userId).add(userId);
            }
            
            // Create a max-heap based on tweet timestamp
            PriorityQueue<Tweet> maxHeap = new PriorityQueue<>(
                (a, b) -> b.timestamp - a.timestamp
            );
            
            // Add the most recent tweet from each followee to the heap
            Set<Integer> followees = following.get(userId);
            for (int followeeId : followees) {
                Tweet tweet = userTweets.get(followeeId);
                if (tweet != null) {
                    maxHeap.offer(tweet);
                }
            }
            
            // Extract the 10 most recent tweets
            List<Integer> newsFeed = new ArrayList<>();
            int count = 0;
            while (!maxHeap.isEmpty() && count < 10) {
                Tweet tweet = maxHeap.poll();
                newsFeed.add(tweet.tweetId);
                count++;
                
                // Add the next tweet from the same user to the heap
                if (tweet.next != null) {
                    maxHeap.offer(tweet.next);
                }
            }
            
            return newsFeed;
        }
        
        /**
         * Follower follows a followee.
         * 
         * @param followerId The ID of the follower
         * @param followeeId The ID of the followee
         */
        public void follow(int followerId, int followeeId) {
            // Ensure the users exist in our data structure
            if (!following.containsKey(followerId)) {
                following.put(followerId, new HashSet<>());
                following.get(followerId).add(followerId);
            }
            if (!following.containsKey(followeeId)) {
                following.put(followeeId, new HashSet<>());
                following.get(followeeId).add(followeeId);
            }
            
            // Add the followee to the follower's set of followees
            following.get(followerId).add(followeeId);
        }
        
        /**
         * Follower unfollows a followee.
         * 
         * @param followerId The ID of the follower
         * @param followeeId The ID of the followee
         */
        public void unfollow(int followerId, int followeeId) {
            // Ensure the users exist in our data structure
            if (!following.containsKey(followerId)) {
                following.put(followerId, new HashSet<>());
                following.get(followerId).add(followerId);
                return;
            }
            
            // Users cannot unfollow themselves
            if (followerId == followeeId) {
                return;
            }
            
            // Remove the followee from the follower's set of followees
            following.get(followerId).remove(followeeId);
        }
    }
    
    /**
     * Main method to demonstrate the solution with example inputs.
     */
    public static void main(String[] args) {
        System.out.println("Example using the standard implementation:");
        
        Twitter twitter = new Twitter();
        
        // User 1 posts a tweet (id = 5)
        twitter.postTweet(1, 5);
        System.out.println("User 1 posts a tweet (id = 5)");
        
        // User 1's news feed should return a list with 1 tweet id -> [5]
        List<Integer> feed1 = twitter.getNewsFeed(1);
        System.out.println("User 1's news feed: " + feed1);
        
        // User 1 follows user 2
        twitter.follow(1, 2);
        System.out.println("User 1 follows user 2");
        
        // User 2 posts a tweet (id = 6)
        twitter.postTweet(2, 6);
        System.out.println("User 2 posts a tweet (id = 6)");
        
        // User 1's news feed should return a list with 2 tweet ids -> [6, 5]
        List<Integer> feed2 = twitter.getNewsFeed(1);
        System.out.println("User 1's news feed: " + feed2);
        
        // User 1 unfollows user 2
        twitter.unfollow(1, 2);
        System.out.println("User 1 unfollows user 2");
        
        // User 1's news feed should return a list with 1 tweet id -> [5]
        List<Integer> feed3 = twitter.getNewsFeed(1);
        System.out.println("User 1's news feed: " + feed3);
        
        System.out.println("\nExample using the optimized implementation:");
        
        TwitterOptimized twitterOpt = new TwitterOptimized();
        
        // User 1 posts a tweet (id = 5)
        twitterOpt.postTweet(1, 5);
        System.out.println("User 1 posts a tweet (id = 5)");
        
        // User 1's news feed should return a list with 1 tweet id -> [5]
        List<Integer> feed1Opt = twitterOpt.getNewsFeed(1);
        System.out.println("User 1's news feed: " + feed1Opt);
        
        // User 1 follows user 2
        twitterOpt.follow(1, 2);
        System.out.println("User 1 follows user 2");
        
        // User 2 posts a tweet (id = 6)
        twitterOpt.postTweet(2, 6);
        System.out.println("User 2 posts a tweet (id = 6)");
        
        // User 1's news feed should return a list with 2 tweet ids -> [6, 5]
        List<Integer> feed2Opt = twitterOpt.getNewsFeed(1);
        System.out.println("User 1's news feed: " + feed2Opt);
        
        // User 1 unfollows user 2
        twitterOpt.unfollow(1, 2);
        System.out.println("User 1 unfollows user 2");
        
        // User 1's news feed should return a list with 1 tweet id -> [5]
        List<Integer> feed3Opt = twitterOpt.getNewsFeed(1);
        System.out.println("User 1's news feed: " + feed3Opt);
        
        // Additional test case with multiple tweets and users
        System.out.println("\nAdditional test case:");
        
        Twitter twitter2 = new Twitter();
        
        // User 1 posts tweets
        twitter2.postTweet(1, 10);
        twitter2.postTweet(1, 20);
        System.out.println("User 1 posts tweets with ids 10 and 20");
        
        // User 2 posts tweets
        twitter2.postTweet(2, 30);
        twitter2.postTweet(2, 40);
        System.out.println("User 2 posts tweets with ids 30 and 40");
        
        // User 1 follows user 2
        twitter2.follow(1, 2);
        System.out.println("User 1 follows user 2");
        
        // User 1's news feed should return [40, 30, 20, 10]
        List<Integer> feed4 = twitter2.getNewsFeed(1);
        System.out.println("User 1's news feed: " + feed4);
        
        // User 3 posts tweets
        twitter2.postTweet(3, 50);
        twitter2.postTweet(3, 60);
        System.out.println("User 3 posts tweets with ids 50 and 60");
        
        // User 1 follows user 3
        twitter2.follow(1, 3);
        System.out.println("User 1 follows user 3");
        
        // User 1's news feed should return [60, 50, 40, 30, 20, 10]
        List<Integer> feed5 = twitter2.getNewsFeed(1);
        System.out.println("User 1's news feed: " + feed5);
        
        // Tracing the execution
        System.out.println("\nTracing the execution of the example:");
        System.out.println("1. Initialize Twitter");
        System.out.println("   - userTweets: {}");
        System.out.println("   - following: {}");
        System.out.println("   - timestamp: 0");
        
        System.out.println("2. User 1 posts a tweet (id = 5)");
        System.out.println("   - Create tweet with id=5, timestamp=0");
        System.out.println("   - Add user 1 to following map: {1: [1]}");
        System.out.println("   - Add tweet to userTweets: {1: [(5, 0)]}");
        System.out.println("   - Increment timestamp to 1");
        
        System.out.println("3. Get User 1's news feed");
        System.out.println("   - Add tweets from user 1 to max-heap: [(5, 0)]");
        System.out.println("   - Extract top tweet: (5, 0)");
        System.out.println("   - News feed: [5]");
        
        System.out.println("4. User 1 follows user 2");
        System.out.println("   - Add user 2 to following map: {1: [1, 2], 2: [2]}");
        
        System.out.println("5. User 2 posts a tweet (id = 6)");
        System.out.println("   - Create tweet with id=6, timestamp=1");
        System.out.println("   - Add tweet to userTweets: {1: [(5, 0)], 2: [(6, 1)]}");
        System.out.println("   - Increment timestamp to 2");
        
        System.out.println("6. Get User 1's news feed");
        System.out.println("   - Add tweets from user 1 and user 2 to max-heap: [(6, 1), (5, 0)]");
        System.out.println("   - Extract top tweet: (6, 1)");
        System.out.println("   - Extract next tweet: (5, 0)");
        System.out.println("   - News feed: [6, 5]");
        
        System.out.println("7. User 1 unfollows user 2");
        System.out.println("   - Remove user 2 from user 1's followees: {1: [1], 2: [2]}");
        
        System.out.println("8. Get User 1's news feed");
        System.out.println("   - Add tweets from user 1 to max-heap: [(5, 0)]");
        System.out.println("   - Extract top tweet: (5, 0)");
        System.out.println("   - News feed: [5]");
    }
}
