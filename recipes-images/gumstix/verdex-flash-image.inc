DESCRIPTION = "A flash image for Gumstix verdex boards."
LICENSE = "MIT"

COMPATIBLE_MACHINE = "verdex"

IMAGE_BOOT_FILES_verdex = "gumstix-factory.script u-boot.bin uImage"
IMAGE_FSTYPES = "jffs2"

#IMAGE_FEATURES += "splash ssh-server-openssh"
#IMAGE_FEATURES += "x11-base"
IMAGE_FEATURES += "read-only-rootfs"
# Uncomment below to include dev tools and packages
# IMAGE_FEATURES += "tools-sdk dev-pkgs"

IMAGE_LINGUAS = "en-us"

inherit image
inherit simulator

addtask do_sizecheck after do_image after before do_build
do_sizecheck() {
  if [ -n "${ROOTFS_MAXSIZE}" ]; then
    size=$(wc -c < ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.jffs2)
    calcmax=$(echo ${ROOTFS_MAXSIZE} | bc)
    bbnote $size + " / " $calcmax
    if [ $size -ge $calcmax ]; then
      bbfatal  "This rootfs (size=$size) is too big for your device ($calcmax). Please reduce the size of the rootfs."
    fi
  fi
}

IMAGE_POSTPROCESS_COMMAND += "do_flashbuild"
do_flashbuild() {
  rootstart=$(echo "(${UBOOT_MAXSIZE})/(2^10)" | bc)
  dtstart=$(echo "(${UBOOT_MAXSIZE} + ${ROOTFS_MAXSIZE})/(2^10)" | bc)
  kernstart=$(echo "(${UBOOT_MAXSIZE} + ${ROOTFS_MAXSIZE} + ${DT_MAXSIZE})/(2^10)" | bc)
  rm -f ${DEPLOY_DIR_IMAGE}/${IMAGE_BASENAME}.flash
  touch ${DEPLOY_DIR_IMAGE}/${IMAGE_BASENAME}.flash
  dd of=${DEPLOY_DIR_IMAGE}/${IMAGE_BASENAME}.flash bs=1k count=32k if=/dev/zero
  dd of=${DEPLOY_DIR_IMAGE}/${IMAGE_BASENAME}.flash bs=1k conv=notrunc if=${DEPLOY_DIR_IMAGE}/u-boot.bin
  dd of=${DEPLOY_DIR_IMAGE}/${IMAGE_BASENAME}.flash bs=1k conv=notrunc seek=$rootstart if=${DEPLOY_DIR_IMAGE}/${IMAGE_BASENAME}-verdex.jffs2
  dd of=${DEPLOY_DIR_IMAGE}/${IMAGE_BASENAME}.flash bs=1k conv=notrunc seek=$dtstart if=${DEPLOY_DIR_IMAGE}/uImage-verdex.dtb
  dd of=${DEPLOY_DIR_IMAGE}/${IMAGE_BASENAME}.flash bs=1k conv=notrunc seek=$kernstart if=${DEPLOY_DIR_IMAGE}/uImage
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


