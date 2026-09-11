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
		JAR_NAME = "SpringRecupeAIProject-0.0.1-SNAPSHOT.jar"
		DOCKER_IMAGE = "atg8915/ai-app:latest"
		SERVER_USER = "ubuntu"
		SERVER_IP = "16.184.46.118"
		APP_DIR = "/home/ubuntu/app"
	}
	stages {
		stage("Repository Checkout"){
			steps{
				echo 'Git Checkout'
				checkout scm
			}
		}

		stage("JDK21 확인"){
			steps {
				sh '''
					java -version
					./gradlew --version
				   '''
			}
		}

		stage("Gradle Permission"){
			steps{
				sh '''
					chmod +x gradlew
				   '''
			}
		}

		stage("Gradlew Build"){
			steps {
				sh '''
					./gradlew clean build -x test
				   '''
			}
		}

		stage("Docker Build"){
			steps{
				sh '''
					docker build -t ${DOCKER_IMAGE} .
				   '''
			}
		}

		stage("DockerHub Login") {
			steps{
				withCredentials([
					usernamePassword(
						credentialsId: 'dockerhub_info',
						usernameVariable: 'DH_USER',
						passwordVariable: 'DH_PASS'
					)
				]){
					sh '''
						echo "$DH_PASS" | docker login -u "$DH_USER" --password-stdin
					   '''
				}
			}
		}

		stage("DockerHub Push") {
			steps {
				sh '''
					docker push ${DOCKER_IMAGE}
				   '''
			}
		}

		stage("SSH Key Setting"){
			steps {
				withCredentials([
					sshUserPrivateKey(
						credentialsId: 'SERVER_SSH_KEY',
						keyFileVariable: 'SSH_KEY',
						usernameVariable: 'SSH_USER'
					)
				]){
					sh '''
						mkdir -p ~/.ssh
						cp "$SSH_KEY" ~/.ssh/id_ed25519
						chmod 600 ~/.ssh/id_ed25519
					   '''
				}
			}
		}

		stage("Known Hosts"){
			steps{
				sh '''
					mkdir -p ~/.ssh
					ssh-keyscan -H 16.184.46.118 >> ~/.ssh/known_hosts
					chmod 644 ~/.ssh/known_hosts
				   '''
			}
		}

		stage("Create .env"){
			steps{
				withCredentials([
					string(
						credentialsId: 'post-url',
						variable: 'POST_URL'
					),
					string(
						credentialsId: 'gen-key',
						variable: 'GEN_KEY'
					),
					sshUserPrivateKey(
						credentialsId: 'SERVER_SSH_KEY',
						keyFileVariable: 'SSH_KEY',
						usernameVariable: 'SSH_USER'
					)
				]){
					sh '''
						ssh -i "$SSH_KEY" -o StrictHostKeyChecking=no ubuntu@16.184.46.118 << EOF
						mkdir -p /home/ubuntu/app
						cd /home/ubuntu/app
						rm -f .env
						echo "SPRING_PROFILES_ACTIVE=prod" > .env
						echo "POST_URL=${POST_URL}" >> .env
						echo "GEN_KEY=${GEN_KEY}" >> .env
						chmod 600 .env
						EOF
					   '''
				}
			}
		}

		stage("Copy Docker-compose"){
			steps{
				withCredentials([
					sshUserPrivateKey(
						credentialsId: 'SERVER_SSH_KEY',
						keyFileVariable: 'SSH_KEY',
						usernameVariable: 'SSH_USER'
					)
				]){
					sh '''
						ssh -i "$SSH_KEY" -o StrictHostKeyChecking=no ubuntu@16.184.46.118 "mkdir -p /home/ubuntu/app"
						scp -i "$SSH_KEY" -o StrictHostKeyChecking=no docker-compose.yml ubuntu@16.184.46.118:/home/ubuntu/app/docker-compose.yml
					   '''
				}
			}
		}

		stage("Deploy"){
			steps {
				withCredentials([
					sshUserPrivateKey(
						credentialsId: 'SERVER_SSH_KEY',
						keyFileVariable: 'SSH_KEY',
						usernameVariable: 'SSH_USER'
					)
				]){
					sh '''
						ssh -i "$SSH_KEY" -o StrictHostKeyChecking=no ubuntu@16.184.46.118 << EOF
						cd /home/ubuntu/app
						docker-compose down
						docker-compose pull
						docker-compose up -d
						EOF
					   '''
				}
			}
		}
	}

	post {
		success {
			echo '==================='
			echo 'Docker Compose 배포 성공'
			echo '==================='
		}
		failure {
			echo '==================='
			echo 'Docker Compose 배포 실패ㅠ'
			echo '==================='
			sh '''
				docker compose ps || true
			   '''
		}
	}
} // pipeline 종료