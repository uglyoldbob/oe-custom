DESCRIPTION = "A custom image for Gumstix verdex boards."
LICENSE = "MIT"

MACHINE = "verdex"

IMAGE_FEATURES += "splash package-management ssh-server-openssh"
IMAGE_FEATURES += "x11-base"
# Uncomment below to include dev tools and packages
# IMAGE_FEATURES += "tools-sdk dev-pkgs"

IMAGE_LINGUAS = "en-us"

inherit core-image

DEPENDS += "mtools-native dosfstools-native parted-native"

SYSTEM_TOOLS_INSTALL = " \
  alsa-utils \
  cpufrequtils \
  systemd-analyze \
  tzdata \
"

DEV_TOOLS_INSTALL = " \
  memtester \
  mtd-utils-ubifs \
  u-boot-mkimage \
"

NETWORK_TOOLS_INSTALL = " \
  curl \
  dnsmasq \
  hostapd \
  iproute2 \
  iputils \
  iw \
  ntp \
  uim \
"

MEDIA_TOOLS_INSTALL = " \
  media-ctl \
  raw2rgbpnm \
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
  packagegroup-cli-tools \
  packagegroup-cli-tools-debug \
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

IMAGE_INSTALL += " \
 florence \
 gnome-bluetooth \
 polkit-gnome \
 polkit-group-rule-network \
 polkit-group-rule-datetime \
"

IMAGE_INSTALL += "gprogs"

# Create a generic 'gumstix' user account, part of the gumstix group,
# using '/bin/sh' and with a home directory '/home/gumstix' (see
# /etc/default/useradd).  We set the password to 'gumstix' and add them
# to the 'sudo' group.
inherit extrausers
EXTRA_USERS_PARAMS = " \
    useradd -P gumstix -G sudo gumstix; \
"


