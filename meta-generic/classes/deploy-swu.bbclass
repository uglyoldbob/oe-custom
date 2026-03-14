do_remote_deploy[network] = "1"
do_remote_deploy() {
	if [ -z "${DEPLOY_SSH_KEY}" ]; then
        bbfatal "DEPLOY_SSH_KEY should be set in local.conf."
    fi
	if [ -z "${DEPLOY_SERVER_PATH}" ]; then
        bbfatal "DEPLOY_SERVER_PATH should be set in local.conf."
    fi
	if [ -z "${DEPLOY_CHMOD}" ]; then
		chmod ${DEPLOY_CHMOD} "${DEPLOY_DIR_IMAGE}/${IMAGE_BASENAME}-${MACHINE}.rootfs.swu"
	fi
	rsync -e "ssh -i ${DEPLOY_SSH_KEY}" \
  		--chmod=u+rw,g+rw,o+r \
  		--perms \
  		"${DEPLOY_DIR_IMAGE}/${IMAGE_BASENAME}-${MACHINE}.rootfs.swu" \
  		"${DEPLOY_SERVER_PATH}"
}
addtask do_remote_deploy after do_swuimage before do_build