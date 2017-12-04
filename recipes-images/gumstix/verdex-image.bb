DESCRIPTION = "A 32M flash image for Gumstix verdex boards."
LICENSE = "MIT"

MACHINE = "verdex"

#max size for 32m flash image
ROOTFS_MAXSIZE = "29065216"
IMAGE_ROOTFS_SIZE = "28384"
IMAGE_BOOT_FILES_verdex = "gumstix-factory.script u-boot.bin uImage"
IMAGE_FSTYPES = "jffs2"


#IMAGE_FEATURES += "splash ssh-server-openssh"
#IMAGE_FEATURES += "x11-base"
IMAGE_FEATURES += "read-only-rootfs"
# Uncomment below to include dev tools and packages
# IMAGE_FEATURES += "tools-sdk dev-pkgs"

IMAGE_LINGUAS = "en-us"

inherit image

addtask do_sizecheck after do_image after before do_build

do_sizecheck() {
  if [ ! -z ${ROOTFS_MAXSIZE} ]; then
    size=$(wc -c < ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.jffs2)
    echo "$size"
    if [ $size -ge ${ROOTFS_MAXSIZE} ]; then
      bbfatal  "This rootfs (size=$size) is too big for your device (${ROOTFS_MAXSIZE}). Please reduce the size of the rootfs."
    fi
  fi
}

IMAGE_INSTALL = "packagegroup-core-boot"

RDEPENDS_${PN} = "\
    base-files \
"

DEPENDS += "mtools-native dosfstools-native parted-native"

# Create a generic 'gumstix' user account, part of the gumstix group,
# using '/bin/sh' and with a home directory '/home/gumstix' (see
# /etc/default/useradd).  We set the password to 'gumstix' and add them
# to the 'sudo' group.
inherit extrausers
EXTRA_USERS_PARAMS = " \
    useradd -P gumstix -G sudo gumstix; \
"


