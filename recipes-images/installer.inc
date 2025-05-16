DESCRIPTION = "A custom image for an installation environment"
LICENSE = "MIT"

#IMAGE_FEATURES += "splash package-management ssh-server-openssh"
#IMAGE_FEATURES += "x11-base"
# Uncomment below to include dev tools and packages
# IMAGE_FEATURES += "tools-sdk dev-pkgs"

IMAGE_LINGUAS = "en-us"

inherit core-image

DEPENDS += "grub-native"

IMAGE_INSTALL += " \
	kernel-base \
	swupdate \
	autoinstall \
	grub \
	efibootmgr \
"

IMAGE_FSTYPES = "tar.gz wic.zst ext4"

ROOTFS_POSTPROCESS_COMMAND += "do_rootfs_fixup;"

do_rootfs_fixup() {
    install -d ${IMAGE_ROOTFS}/etc
    cp ${DEPLOY_DIR_IMAGE}/${SWU_TO_DEPLOY} ${IMAGE_ROOTFS}/etc/initial.swu
    echo "${INSTALL_DRIVE}" >> ${IMAGE_ROOTFS}/etc/initialize-mmc
    install -d ${IMAGE_ROOTFS}/boot/EFI/BOOT/grub
    grub-editenv ${IMAGE_ROOTFS}/boot/grubenv create
}

# Create a user account with a password
# this command generates a hash for the useradd command
# the password is prompted interactively
# mkpasswd -m sha256crypt
inherit extrausers
EXTRA_USERS_PARAMS = " \
    useradd -p '$5$SbHarFQFMohIwIjP$4KaSsQDCraqEGzJewZk.2BYBzpVhxdx4EX/omngxF35' -G sudo xbox; \
"


