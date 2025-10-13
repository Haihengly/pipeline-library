def call(Map config) {
    def STORE_DIR = "/My-Docker/Dev-Service"
    def PROJECT_NAME = env.JOB_NAME.tokenize('/')[0].toLowerCase().replaceAll('[^a-z0-9_-]', '-')
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
            case "master":
                envFile = ".env.master"
                break
            case "d":
                envFile = ".env.d"
                break
            case "t":
                envFile = ".env.t"
                break
            default:
                error "Unknown environment: ${config.envName}"
        }
    def composeFiles = "-p ${PROJECT_NAME}-${config.envName} -f docker-compose.base.yml -f docker-compose.${config.envName}.yml"
    // def composeFiles = "-f docker-compose.base.yml -f docker-compose.${config.envName}.yml"

    echo 'Starting containers...'
    sh """
        cd ${STORE_DIR}
        export BUILD_DIR=${config.FOLDER}-${config.envName}
        docker compose ${composeFiles} --env-file ${envFile} up -d
    """
}