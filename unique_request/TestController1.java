/**
 * 在高并发场景下依然会有幂等性问题，因为没有充分利用redis的原子性
 */
@RestController
public class TestController1 {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 提交接口，需要携带有效的token参数
     */
    @PostMapping("/submit")
    public String submit(@RequestParam("token") String token) {
        // 检查Token是否有效
        if (!isValidToken(token)) {
            return "Invalid token";
        }

        // 具体的接口处理逻辑，在这里实现你的业务逻辑

        // 使用完毕后删除Token
        deleteToken(token);

        return "Success";
    }

    /**
     * 检查Token是否有效
     */
    private boolean isValidToken(String token) {
        // 检查Token是否存在于Redis中
        return redisTemplate.hasKey(token);
    }

    /**
     * 删除Token
     */
    private void deleteToken(String token) {
        // 从Redis中删除Token
        redisTemplate.delete(token);
    }

    /**
     * 生成Token接口，用于获取一个唯一的Token
     */
    @GetMapping("/generateToken")
    public String generateToken() {
        // 生成唯一的Token
        String token = UUID.randomUUID().toString();

        // 将Token保存到Redis中，并设置过期时间（例如10分钟）
        redisTemplate.opsForValue().set(token, "true", Duration.ofMinutes(10));

        return token;
    }
}

