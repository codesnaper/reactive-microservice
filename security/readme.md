### Security

For Using in other module:<br>
1. Make a `@ComponentScan("org.security")`
2. For Storing User in db provide LocalContainerEntityManagerFactoryBean.class Bean or add utility module along with common-entity for User, role and user role mapping.
3. For Default User and password don't add Utilty module or common-entity and provide properties <br>
```properties
authentication.default.user=shubham
authentication.default.password=password
authentication.default.role=USER
```
4. For Using basic Authentication, provide properties:
```properties
authentication.basic=true
```
5. For taking default webfilter and path pattern to include
```properties
authentication.basicauto=true
```
6. For using JWTToken authentication
```properties
authentication.jwt=true
```
and also create jwt.properties
```properties
jwt.expiration.second=1800
jwt.issuer=product
jwt.roleName=roles
jwt.algorithm=HS256
jwt.secretValue={noop}shubhamkhandelwaldhgvhshdghsdhvusdch ghgdsghg chxghgvhsdghghvgdhcgvhdsghghdvgsdhghzv
```
7. For auto enable JWT Web Filter:
```properties
authentication.jwtauto=true
```
8. For adding path pattern match
```properties
authentication.path.pattern=/product-api/**,/product-api/product/**,
authentication.open.path.pattern=/actuator,/actuator/health,/actuator/info
authentication.token.path=/token
```
9. For JWT Filter inject bean and add in Httpconfig filter: 
```java
    @Autowired
    @Qualifier("JWTAuthenticationWebFilter")
    private AuthenticationWebFilter jwtAuthenticationWebFilter;

    @Autowired
    @Qualifier("BearerAuthenticationFilter")
    private AuthenticationWebFilter bearerAuthenticationFilter;
```
10. FOR Basic auth filter inject bean
```java
@Autowired
private ReactiveUserDetailsService reactiveUserDetailsService;
```
