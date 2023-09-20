/**
 * 使用setIfAbsent方法来尝试将Token保存到Redis中，并设置过期时间（例如10分钟）。
 * 如果设置成功，则执行具体的接口处理逻辑，处理完成后会自动删除Token。如果设置失败，说明该Token已存在，即重复提交，直接返回错误信息
 * 
 * 删除Token的操作在finally块中执行，无论接口处理逻辑成功与否都会确保删除Token，以免出现异常导致未能正确删除Token的情况
 */
@RestController
public class TestController2 {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 提交接口，需要携带有效的token参数
     */
    @PostMapping("/submit")
    public String submit(@RequestParam("token") String token) {
        // 使用SETNX命令尝试将Token保存到Redis中，如果返回1表示设置成功，说明是第一次提交；否则返回0，表示重复提交
        Boolean success = redisTemplate.opsForValue().setIfAbsent(token, "true", Duration.ofMinutes(10));
        if (success == null || !success) {
            return "Duplicate submission";
        }

        try {
            // 具体的接口处理逻辑，在这里实现你的业务逻辑

            return "Success";
        } finally {
            // 使用DEL命令删除Token
            redisTemplate.delete(token);
        }
    }
}
