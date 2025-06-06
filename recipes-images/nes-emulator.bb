DESCRIPTION = "A custom image for an NES emulator."
LICENSE = "MIT"

#IMAGE_FEATURES += "splash package-management ssh-server-openssh"
#IMAGE_FEATURES += "x11-base"
# Uncomment below to include dev tools and packages
# IMAGE_FEATURES += "tools-sdk dev-pkgs"

IMAGE_LINGUAS = "en-us"

IMAGE_FSTYPES = "tar.gz tar.gz.enc wic wic.gz wic.zst"

inherit core-image swupdate-enc

FILESEXTRAPATHS:prepend := "${TOPDIR}:"

SRC_URI += "file://conf/keys.conf"

IMAGE_FEATURES += "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'weston', \
       bb.utils.contains('DISTRO_FEATURES',     'x11', 'x11-base x11-sato', \
                                                       '', d), d)} \
"

IMAGE_INSTALL += " \
       	${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'xterm', '', d)} \
	${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'weston-init', '', d)} \
	${@bb.utils.contains('DISTRO_FEATURES', 'x11 wayland', 'xwayland', '', d)} \
	${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland', '', d)} \
"

IMAGE_INSTALL += " \
	kernel-base \
	swupdate \
	swupdate-www \
	openssh \
	alsa-utils \
	espeak \
	pulseaudio-server \
	pulseaudio-lib-alsa-util \
"

#alsaloop -C bluealsa:DEV=(address),PROFILE=a2dp -P plughw:0,3 -r 32000 -c 2 -f S16_LE --sync=simple -t 50000 -T 1

# Create a user account with a password
# this command generates a hash for the useradd command
# the password is prompted interactively
# mkpasswd -m sha256crypt
inherit extrausers
EXTRA_USERS_PARAMS = " \
    useradd -p '$5$SbHarFQFMohIwIjP$4KaSsQDCraqEGzJewZk.2BYBzpVhxdx4EX/omngxF35' -G sudo xbox; \
"


