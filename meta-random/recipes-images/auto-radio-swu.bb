DESCRIPTION = "SWU image for automotive radio"
SECTION = ""

# Note: sw-description is mandatory
SRC_URI = " \
	file://sw-description \
"

inherit swupdate

SWUPDATE_SIGNING = "RSA"
SWUPDATE_IMAGES_ENCRYPTED = "True"
SWUPDATE_ENCRYPT_SWDESC = "True"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# IMAGE_DEPENDS: list of Yocto images that contains a root filesystem
# it will be ensured they are built before creating swupdate image
IMAGE_DEPENDS = "auto-radio"

# SWUPDATE_IMAGES: list of images that will be part of the compound image
# the list can have any binaries - images must be in the DEPLOY directory
SWUPDATE_IMAGES = " \
	auto-radio-${MACHINE}.rootfs.tar.gz.enc \
"

# Images can have multiple formats - define which image must be
# taken to be put in the compound image
SWUPDATE_IMAGES_FSTYPES[auto-radio] = ".tar.gz.enc"

