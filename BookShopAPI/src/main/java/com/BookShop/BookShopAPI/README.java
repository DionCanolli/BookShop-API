package com.BookShop.BookShopAPI;

public class README {
    /* ALBANIAN
           |
          \|/

    1 based index:
        Pageable pageable = PageRequest.of(0, 1);
        if (size != null || pageNumber != null) {
            if (pageNumber - 1 < 0)
                pageable = PageRequest.of(0, size);
            else
                pageable = PageRequest.of(pageNumber - 1, size);
        }


        Per me perdor JWT Token Authentication/Authorization na duhet mi bo 2 detyra: 1. me gjeneru JWT Token nese useri
        nuk eshte bere login, 2. me validu nese useri eshte bere login:

        1. I kemi shtu dependencyt ne pom.xml.
        2. Ne spring security config file, kemi shtu:
           httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
           Me ane se te ciles i themi Spring Securityt qe te mos krijoj HTTP session kur te bohemi login sepse
           po dojna qe na me kry kete pune (pra custom permes JWT Tokenave). Spring Security by default e bojke
           JSESSIONID qe eshte token qe e ka perdor kur bohesh login niher, ski nevoj apet, po e perdor kete token per
           requesta. (Shiko SecurityConfig)
        3. corsConfiguration.setExposedHeaders(Arrays.asList("Authorization")); kjo sherben per me ekspozu header
           parametrin Authorization klientit (front-endit) qe me pas qasje me lexu vleren e keti headeri. (Opcionale
           Shiko SecurityConfig)
        4. Gjenerimi i JWT Tokenit, per kta e kemi bere nje class: JWTTokenGenerator, e cila permes metodes
           generateJWTToken e gjeneron nje JWT Token qe zgat 24h dhe kete duhet me ja qu responses ne header, te ruajtur
           ne parametrin Authorization.
        5. Duhet me bo nje request filter JWTGeneratorFilter, i cili nje her gjat egzekutimit, nese useri u bo login,
           atehere ja qon kete jwt token atij, qe mos me pas nevoj mi shti te dhenat pasi u bo login, por e perdor
           kete token per cdo request tjeter. Kete filter e ben mu egzekutu vetem per urln /login, sepse aty na duhet
           me ja qu JWT Tokenit userit qe eshte duke u bere login.
        6. Kete filter e bejme mu egzekutu pas autentikimit, pra pas BasicAuthenticationFilter-it (Shiko SecurityConfig)
        7. Tash per me validu tokenin, pra per me mujt me kqyr se useri a ka token a jo, e kemi bere filterin
           JWTValidatorFilter ku ne te cilin fillimisht e kemi marr header parametrin e requestit te quajtur: Authorization
           sepse ne te ruhet JWT Tokeni, dhe nese ku parameter egziston, atehere ja kemi marr Claims (te dhenat e ruajtura
           ne kete JWT Token) te cilat jane username dhe authorities. Pastaj e kemi kriju nje Authentication objekt
           te tipit UsernamePasswordAuthenticationToken, ja kemi jep te dhenat dhe e kemi ruajtur ne context ku ruhet
           authentication objekti. Ktu eshte nje send me rendesi, kur i merr claims te userit te bere login, tek
           authorities, kur i merr dhe i ruan ne string: String.valueOf(claims.get("authorities")) kjo e ka vleren
           psh [ROLE_USER], e per me ru ne SecurityContextHolder te duhet me rujt si Collection<SimpleGrantedAuthority>
           e nese veq e shtin ne kete collection si ne kete menyre:

            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(String.valueOf(claims.get("authorities"))));

            Atehere kjo e ruan si [[ROLE_USER]] dhe nuk e njeh SecurityFilterChain, per kete arsye fillimisht ja largojm
            [] e pastaj e ruajme ne Collection<SimpleGrantedAuthority>:

            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            String authority = String.valueOf(claims.get("authorities"));
            authority = authority.substring(1, authority.length() - 1);  <--  Kjo i largon [] nga claims authorities
            authorities.add(new SimpleGrantedAuthority(authority));

           Mirpo e kemi bere kete filter te egzekutohet gjithkun porpos ne /login, sepse aty duhet te gjenerohet tokeni
           e ska nevoj qe te verifikohet.
        8. Tash kete filter e bejme para BasicAuthenticationFilter, sepse nese ka token, atehere ska nevoj per mu
           autentifiku (Shiko SecurityConfig)
        9. Tash masi qe spring security e ben procesin e loginit me at default formen, na po dojna qe me bo jo me form,
           po me api, duke e shti emailen dhe passwordin ne body te requestit per login: /v1/login. Per me bo kta, na duhet
           me kriju nje filter te tipit UsernamePasswordAuthenticationFilter -> CustomUsernamePasswordAuthenticationFilter
           ku ne te cilin, do ta vendosim fillimisht si url te loginit: /v1/login, e pastaj ne metoden attemptAuthentication
           do ta krijojme nje Authentication object UsernamePasswordAuthenticationToken dhe do tia vendosim emailen dhe
           passwordin e marr nga requesti. E pastaj do tia japim AuthenticationManagerit qe ta kryej procesin e autentikimit
           Dhe nese autetifikimi shkon me sukses, e kemi bere qe te egzekutohet filteri JWTGeneratorFilter se duhet me ja jep
           userit te autentifikuar JWT Tokenin e tij qe ta perdor ate e te mos ket nevoj te autetifikohet serish.
       10. Pastaj do ta fusim kete filter: CustomUsernamePasswordAuthenticationFilter te egzekutohet para
           UsernamePasswordAuthenticationFilter-it. (Shiko SecurityConfig)
       11. Tash per logout, na duhet me invalidu jwt tokenin, masi me fshi direkt nuk mundemi, atehere e kemi bere nje
           endpoint ne AuthController: /v1/logout, dhe kur egzekutohet kjo, nese ka jwt token ne Authorization header ne
           request, atehere e ruan ne databaz ne nje collection: jWTToken, dhe tash nese jemi bere logout, dhe tentojm
           qe te egzekutojm requestat me ate jwt token, nese egziston ne db, i bie qe jemi bere logout, dhe nuk e pranon
           ate request me ate token ne Authorization header. Cka kemi bo:
            1. JWTToken - collectioni qe ka mi rujt tokenat qe jemi bere logout
            2. JWTRepository - dihet
            3. TokenBlacklistService - kena bo metoden me rujt ate JWTToken ne db, dhe me kqyr a egziston
            4. JWTGeneratorFilter - nese Authorization headeri i requestit nuk ka JWT token, apo e ka por egziston ne
                                    db (pasi qe jemi bere logout) atehere dhe vetem atehere e krijon nje JWT token tjeter
            5. JWTValidatorFilter - nese Authorization headeri i requestit egziston dhe nuk eshte empty, dhe nese nuk
                                    egziston ne db, atehere mund ta perdorim ate jwt token per requesta.

           Vlen per mua: Work flow i autentikimit te nje useri: Authentication objekti permes AuthenticationManagerit,
           AuthenticationProviderit, UserDetailsService, UserDetails, .. e ki te kursi i spring securityt te msimi 45.1)

     */
}
