# 个人网站项目

这是一个个人网站项目，包含前端和后端实现，使用了现代技术栈来构建高效、响应迅速的应用。

## 技术栈

- **前端**: 
  - React
  - Echarts
  - Node.js
 
- **后端**: 
  - Spring Boot
  - MySQL
  - Mybatis-Plus
  - Node.js
  - Redis
  - Tomcat (用于服务器部署)

- **其他**:
  - 腾讯云对象存储 (用于文件存储和管理)
  - Nginx (用于反向代理和负载均衡)

## 后台功能

- **用户管理**: 
- **管理员管理**: 
- **图片管理**: 
- **资源管理**: 

## 部署

1. **后端部署**:
   - 使用 Spring Boot 构建和运行后端服务。
   - 使用 MySQL 作为数据库，Redis 用于缓存。

2. **前端部署**:
   - 使用 React 构建前端应用。
   - 配置 Nginx 进行反向代理和负载均衡。

3. **对象存储**:
   - 配置腾讯云对象存储以管理文件和用户上传的内容。

## 技术栈

## 后端启动
后端启动前需要初始化配置application-prod.yml。主要是需要配置：
## 配置提示

### 1. 服务器端口
- **配置项**: `server.port`
- **说明**: 应用程序运行的端口。默认 `8080`。

### 2. SMTP 邮件服务器
- **配置项**: `spring.mail`
- **说明**: 配置邮件发送功能。
  - `port`: SMTP 端口号（例如 `587`）。
  - `username`: 邮箱账号。
  - `password`: 邮箱密码。
  - `host`: SMTP 服务器地址（例如 `smtp.qq.com`）。

### 3. Redis 配置
- **配置项**: `spring.redis`
- **说明**: 配置 Redis 连接。
  - `host`: Redis 服务器地址。
  - `port`: Redis 端口号（默认 `6379`）。
  - `password`: Redis 密码（如果设置）。

### 4. 数据库配置
- **配置项**: `dynamicDataSource.default`
- **说明**: 配置数据库连接。
  - `url`: 数据库连接 URL（例如 `jdbc:mysql://host:port/database`）。
  - `username`: 数据库用户名。
  - `password`: 数据库密码。

### 5. JWT 配置
- **配置项**: `jwt`
- **说明**: 配置 JSON Web Token (JWT)。
  - `header`: JWT 令牌的返回头部（例如 `Authorization`）。
  - `tokenPrefix`: JWT 令牌前缀（例如 `Bearer `）。
  - `secret`: JWT 私钥。
  - `expireTime`: JWT 有效时间（毫秒）。

### 6. 文件上传配置
- **配置项**: `spring.servlet.multipart`
- **说明**: 配置文件上传限制。
  - `max-file-size`: 单个文件最大上传大小。
  - `max-request-size`: 请求最大大小（包括所有文件）。
## 前端启动
进入anakki-manager模组下的"./web/a-manager/"目录下执行 npm install，然后执行npm start。此目录为前端项目的根目录，理论上a-manager目录可以迁移到任何地方。和后端项目没有任何文件夹路径的关系。


