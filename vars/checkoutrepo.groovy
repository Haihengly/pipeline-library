def call(Map config) {
    def STORE_DIR = "/My-Docker/Dev-Service"

    dir("${env.WORKSPACE}/.scm-detect") {
      checkout([$class: 'GitSCM',
        branches: [[name: config.branch]],
        userRemoteConfigs: [[url: config.REPO_URL]]
      ])
    }

    sh """
      rm -rf ${STORE_DIR}/${config.FOLDER}-${config.envName}
      mkdir -p ${STORE_DIR}/${config.FOLDER}-${config.envName}
      cp -r ${env.WORKSPACE}/.scm-detect/* ${STORE_DIR}/${config.FOLDER}-${config.envName}
      ls ${env.WORKSPACE}/.scm-detect
    """

}