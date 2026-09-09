pipeline {
	/*
		소기업 : Git Action
		중소기업 : Jenkins
		대기업 : 자체 처리
			= docker , docker-compose
		전체 동작 : Jenkins = 관리자
		Git Push
		   |------ workflows(Git)
		   |------ WebHook
		Jenkins
		   |------ Permission 방지
		   		   chmod +x gradlew : 실행권한
		Gradel Build
		   |------ ./gradlew clean build -x test test제외 jar
		Docker Build
		   |------ image만든다 docker build -t image명
		Docker Hun Push docker push image명
		   |------ 서버 종료
		Docker compose down
		   |
		Docker compose Pull
		   |
		Docker compose up -d
		 
	*/
	agent any
	environment {
		APP_DIR = "~/app",
		JAR_NAME = "SpringRecupeAIProject-0.0.1-SNAPSHOT.jar"
		DOCKER_IMAGE = "atg8915/ai-app:latest"
	}
	// 우분투 (AWS) 명령어 수행
	stages {
		// 1. Git Checkout : Repository 확인
		stage("Repository Checkout"){
			steps{
				echo 'Git Checkout'
				checkout scm
			}
		}
	}
}
post {
	
}