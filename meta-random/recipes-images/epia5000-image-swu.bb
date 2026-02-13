DESCRIPTION = "Image for installing an epia5000 image"
SECTION = ""

COMPATIBLE_MACHINE = "epia5000"

# Note: sw-description is mandatory
SRC_URI = " \
	file://sw-description \
"

SWUPDATE_SIGNING = "RSA"
SWUPDATE_IMAGES_ENCRYPTED = "True"
SWUPDATE_ENCRYPT_SWDESC = "True"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# IMAGE_DEPENDS: list of Yocto images that contains a root filesystem
# it will be ensured they are built before creating swupdate image
IMAGE_DEPENDS = "epia5000-image"

# SWUPDATE_IMAGES: list of images that will be part of the compound image
# the list can have any binaries - images must be in the DEPLOY directory
SWUPDATE_IMAGES:epia5000 = " \
	epia5000-image \
"

# Images can have multiple formats - define which image must be
# taken to be put in the compound image
SWUPDATE_IMAGES_FSTYPES[epia5000-image] = ".rootfs.tar.gz.enc"

inherit swupdate



