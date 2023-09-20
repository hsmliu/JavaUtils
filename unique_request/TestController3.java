/**
 * 使用Lua脚本配合Redis的原子性操作来实现更可靠的幂等性控制
 * 
 * 
 */
@RestController
public class TestController3 {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 提交接口，需要携带有效的token参数
     */
    @PostMapping("/submit")
    public String submit(@RequestHeader("token") String token) {
        if (StringUtils.isBlank(token)) {
            return "Missing token";
        }

        DefaultRedisScript<Boolean> script = new DefaultRedisScript<>(LUA_SCRIPT, Boolean.class);

        // 使用Lua脚本执行原子性操作
        Boolean success = redisTemplate.execute(script, Collections.singletonList(token), "true", "600");
        if (success == null || !success) {
            // 令牌验证失败，直接返回
            return "Duplicate submission";
        }

        // 具体的接口处理逻辑，在这里实现你的业务逻辑
        
        return "Success";
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

    // Lua脚本
    // if redis.call('SETNX', KEYS[1], ARGV[1]) == 1 then：使用 Redis 的 SETNX 命令，在键 KEYS[1] 中设置值为 ARGV[1]（ARGV 是一个参数数组）。如果 SETNX 返回值为 1（表示设置成功），则执行以下代码块。

    // redis.call('EXPIRE', KEYS[1], ARGV[2])：使用 Redis 的 EXPIRE 命令，在键 KEYS[1] 设置过期时间为 ARGV[2] 秒。

    // return true：返回布尔值 true 给调用方，表示设置和过期时间设置都成功。

    // else：如果 SETNX 返回值不为 1，则执行以下代码块。

    // return false：返回布尔值 false 给调用方，表示设置失败。

    private final String LUA_SCRIPT = "if redis.call('SETNX', KEYS[1], ARGV[1]) == 1 then\n" +
            "    redis.call('EXPIRE', KEYS[1], ARGV[2])\n" +
            "    return true\n" +
            "else\n" +
            "    return false\n" +
            "end";
}

