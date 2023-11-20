//package com.campushare.RideRecommendation.services;
//
//public class RealTimePostUpdateService {
//    private final SimpMessagingTemplate messagingTemplate;
//
//    @Autowired
//    public RealTimePostUpdateService(SimpMessagingTemplate messagingTemplate) {
//        this.messagingTemplate = messagingTemplate;
//    }
//
//    public void broadcastPostCreated(PostEventData eventData) {
//        // This would broadcast the new post to all users that have this post's creator on their recommendation list
//        messagingTemplate.convertAndSend("/topic/posts", eventData);
//    }
//
//    public void broadcastPostUpdated(PostEventData eventData) {
//        // Similarly, this would broadcast the updated post details
//        messagingTemplate.convertAndSend("/topic/posts", eventData);
//    }
//}
