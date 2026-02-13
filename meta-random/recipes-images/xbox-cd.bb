DESCRIPTION = "A custom image for the xbox."
LICENSE = "MIT"

IMAGE_FEATURES += "read-only-rootfs"
#IMAGE_FEATURES += "splash package-management ssh-server-openssh"
#IMAGE_FEATURES += "x11-base"
# Uncomment below to include dev tools and packages
# IMAGE_FEATURES += "tools-sdk dev-pkgs"

IMAGE_LINGUAS = "en-us"

inherit core-image

SYSTEM_TOOLS_INSTALL = " \
  alsa-utils \
  cpufrequtils \
  tzdata \
"

DEV_TOOLS_INSTALL = " \
  memtester \
  mtd-utils-ubifs \
  u-boot-mkimage \
"

NETWORK_TOOLS_INSTALL = " \
  curl \
  hostapd \
  iproute2 \
  iputils \
  iw \
  uim \
"

MEDIA_TOOLS_INSTALL = " \
  media-ctl \
  v4l-utils \
  yavta \
"

GRAPHICS_LIBS = " \
  mtdev \ 
  tslib \
"  

UTILITIES_INSTALL = " \
  coreutils \
  diffutils \
  findutils \
  gpsd \
  grep \
  gzip \
  less \
  nano \
  sudo \
  tar \
  vim \
  wget \
  zip \
"
 
IMAGE_INSTALL += " \
  ${SYSTEM_TOOLS_INSTALL} \
  ${DEV_TOOLS_INSTALL} \
  ${NETWORK_TOOLS_INSTALL} \
  ${MEDIA_TOOLS_INSTALL} \
  ${GRAPHICS_LIBS} \
  ${UTILITIES_INSTALL} \
"

IMAGE_FSTYPES = "wic wic.bmap"
WKS_FILE="xbox-cd.wks"

IMAGE_INSTALL += "cromwell-boot cromwell-kernel"

# Create a generic 'gumstix' user account, part of the gumstix group,
# using '/bin/sh' and with a home directory '/home/gumstix' (see
# /etc/default/useradd).  We set the password to 'gumstix' and add them
# to the 'sudo' group.
inherit extrausers
EXTRA_USERS_PARAMS = " \
    useradd -p '$5$NtusLse5Nrwdg9Ht$g5RSdczawQXABdkHKludnjT3rihSmsTM71atTgwyqD1' -G sudo xbox; \
"


