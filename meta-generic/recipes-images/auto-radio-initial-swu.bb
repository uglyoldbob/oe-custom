DESCRIPTION = "SWU image for automotive radio"
SECTION = ""

# Note: sw-description is mandatory
SRC_URI = " \
	file://sw-description \
"

inherit system-version swupdate
PV = "${SYSTEM_VERSION}"

IMAGE_BASENAME = "${PN}-${PV}"

SWUPDATE_SIGNING = "RSA"
SWUPDATE_IMAGES_ENCRYPTED = "True"
SWUPDATE_ENCRYPT_SWDESC = "True"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# IMAGE_DEPENDS: list of Yocto images that contains a root filesystem
# it will be ensured they are built before creating swupdate image
IMAGE_DEPENDS = "auto-radio imx-boot"

# SWUPDATE_IMAGES: list of images that will be part of the compound image
# the list can have any binaries - images must be in the DEPLOY directory
SWUPDATE_IMAGES = " \
	auto-radio \
	imx-boot \
	u-boot-initial-env-${MACHINE}-sd \
"

addtask boot_copy before do_swuimage after do_unpack

do_boot_copy[depends] = "imx-boot:do_deploy"

do_boot_copy() {
	cp ${DEPLOY_DIR_IMAGE}/imx-boot ${WORKDIR}/${PN}
	cp ${DEPLOY_DIR_IMAGE}/u-boot-initial-env-${MACHINE}-sd ${WORKDIR}/${PN}
}

SWUPDATE_IMAGES_FSTYPES[auto-radio] = ".rootfs.tar.gz"
SWUPDATE_IMAGES_ENCRYPTED[auto-radio] = "1"

inherit deploy-swu