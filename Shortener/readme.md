[URLShortener](https://github.com/pulgeun/Shortener/new/master/Shortener) gradle 프로젝트로 구성 되었습니다.
프로젝트 다운로드 후 

##프로젝트 다운로드
프로젝트 다운로드 후 gradle을 통해서 build 합니다.
* gradlew build
* build가 완료 되면 해당 프로젝트의 build/libs/ 안에 war 파일이 생성됨을 확인 할수 있습니다.
* 해당 war 파일을 tomcat 등 웹서버에 올려서 구성 시킵니다.
* docuement root path는 "/"로 설정 합니다.

##tomcat server.xml
==
<Context docBase="Shortener" path="/" reloadable="true" source="org.eclipse.jst.j2ee.server:Shortener"/></Host>

