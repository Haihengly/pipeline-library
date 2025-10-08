def call(Map config) { 
    def STORE_DIR = "/My-Docker/Dev-Service"
    def PROJECT_NAME = PROJECT_NAME.toLowerCase().replaceAll('[^a-z0-9_-]', '-')
    def envFile
        switch(config.envName) {
            case "main":
                envFile = ".env.main"
                break
            case "dev":
                envFile = ".env.dev"
                break
            case "test":
                envFile = ".env.test"
                break
            default:
                error "Unknown environment: ${config.envName}"
        }
    def composeFiles = "-p ${PROJECT_NAME}-${config.envName} -f docker-compose.base.yml -f docker-compose.${config.envName}.yml"

    echo "Building Docker images with docker-compose..."
    sh """
        cd ${STORE_DIR}
        export BUILD_DIR=${config.FOLDER}-${config.envName}
        docker compose ${composeFiles} --env-file ${envFile} version
        docker compose ${composeFiles} --env-file ${envFile} build
    """
}