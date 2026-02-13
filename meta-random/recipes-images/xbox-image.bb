DESCRIPTION = "A custom image for the xbox."
LICENSE = "MIT"

#IMAGE_FEATURES += "splash package-management ssh-server-openssh"
#IMAGE_FEATURES += "x11-base"
# Uncomment below to include dev tools and packages
# IMAGE_FEATURES += "tools-sdk dev-pkgs"

IMAGE_LINGUAS = "en-us"

inherit core-image

SYSTEM_TOOLS_INSTALL = " \
  alsa-utils \
  tzdata \
"

DEV_TOOLS_INSTALL = " \
  mtd-utils-ubifs \
  u-boot-mkimage \
"

NETWORK_TOOLS_INSTALL = " \
  curl \
  iproute2 \
  iputils \
  iw \
"

GRAPHICS_LIBS = " \
  mtdev \ 
"  

UTILITIES_INSTALL = " \
  coreutils \
  diffutils \
  findutils \
  grep \
  gzip \
  less \
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
  ${GRAPHICS_LIBS} \
  ${UTILITIES_INSTALL} \
"

IMAGE_FSTYPES = "wic wic.bmap"
WKS_FILE="xbox-hd.wks"

#IMAGE_INSTALL += "gprogs"
#IMAGE_INSTALL += "cromwell-boot"

# Create a generic 'gumstix' user account, part of the gumstix group,
# using '/bin/sh' and with a home directory '/home/gumstix' (see
# /etc/default/useradd).  We set the password to 'gumstix' and add them
# to the 'sudo' group.
inherit extrausers
EXTRA_USERS_PARAMS = " \
    useradd -p '$5$SbHarFQFMohIwIjP$4KaSsQDCraqEGzJewZk.2BYBzpVhxdx4EX/omngxF35' -G sudo xbox; \
"


