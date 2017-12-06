DESCRIPTION = "A 32M flash image for Gumstix verdex boards."
LICENSE = "MIT"

MACHINE = "verdex"

#max size for 32m flash image
ROOTFS_MAXSIZE = "29065216"
IMAGE_ROOTFS_SIZE = "28384"
IMAGE_BOOT_FILES_verdex = "gumstix-factory.script u-boot.bin uImage"
IMAGE_FSTYPES = "jffs2"

DEPENDS += "qemu-native"

#IMAGE_FEATURES += "splash ssh-server-openssh"
#IMAGE_FEATURES += "x11-base"
IMAGE_FEATURES += "read-only-rootfs"
# Uncomment below to include dev tools and packages
# IMAGE_FEATURES += "tools-sdk dev-pkgs"

IMAGE_LINGUAS = "en-us"

inherit image

addtask do_sizecheck after do_image after before do_build
IMAGE_POSTPROCESS_COMMAND += "do_flashbuild"

do_sizecheck() {
  if [ ! -z ${ROOTFS_MAXSIZE} ]; then
    size=$(wc -c < ${DEPLOY_DIR_IMAGE}/${IMAGE_LINK_NAME}.jffs2)
    echo "$size"
    if [ $size -ge ${ROOTFS_MAXSIZE} ]; then
      bbfatal  "This rootfs (size=$size) is too big for your device (${ROOTFS_MAXSIZE}). Please reduce the size of the rootfs."
    fi
  fi
}

do_flashbuild() {
  touch ${DEPLOY_DIR_IMAGE}/${IMAGE_BASENAME}.flash
  dd of=${DEPLOY_DIR_IMAGE}/${IMAGE_BASENAME}.flash bs=1k count=32k if=/dev/zero
  dd of=${DEPLOY_DIR_IMAGE}/${IMAGE_BASENAME}.flash bs=1k conv=notrunc if=${DEPLOY_DIR_IMAGE}/u-boot.bin
  dd of=${DEPLOY_DIR_IMAGE}/${IMAGE_BASENAME}.flash bs=1k conv=notrunc seek=256 if=${DEPLOY_DIR_IMAGE}/${IMAGE_BASENAME}-verdex.jffs2
  dd of=${DEPLOY_DIR_IMAGE}/${IMAGE_BASENAME}.flash bs=1k conv=notrunc seek=28640 if=${DEPLOY_DIR_IMAGE}/uImage-verdex.dtb
  dd of=${DEPLOY_DIR_IMAGE}/${IMAGE_BASENAME}.flash bs=1k conv=notrunc seek=28672 if=${DEPLOY_DIR_IMAGE}/uImage
}

addtask runqemu
python do_runqemu() {
    deploy_dir = d.getVar('DEPLOY_DIR_IMAGE', True)
    image_name = d.getVar('IMAGE_BASENAME', True)
    flashname = "%s/%s.flash" % (deploy_dir, image_name)
    oe_terminal("${SHELL} -c \"qemu-system-arm -M verdex -pflash %s -monitor null -nographic -m 289\"" % flashname, "Running qemu", d)
    bb.fatal("This error is so this task will always run")
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


