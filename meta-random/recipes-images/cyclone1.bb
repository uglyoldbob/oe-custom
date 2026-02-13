DESCRIPTION = "A custom image for cyclone de0 nano dev board"
LICENSE = "MIT"

IMAGE_FEATURES += "splash package-management ssh-server-openssh"
IMAGE_FEATURES += "x11-base"
# Uncomment below to include dev tools and packages
# IMAGE_FEATURES += "tools-sdk dev-pkgs"

WKS_FILE = "sdimage-cyclone.wks"
IMAGE_FSTYPES = "wic"

IMAGE_LINGUAS = "en-us"

inherit core-image
#inherit simulator

do_image_wic[depends] += "mtools-native:do_populate_sysroot dosfstools-native:do_populate_sysroot"

addtask builduboot after do_rootfs before do_image
do_builduboot () {
  dd if=${DEPLOY_DIR_IMAGE}/u-boot-with-spl.sfp of=${DEPLOY_DIR_IMAGE}/custom.img
  dd if=${DEPLOY_DIR_IMAGE}/u-boot-dtb.img of=${DEPLOY_DIR_IMAGE}/custom.img bs=512 seek=2432
}

addtask copydtb after do_rootfs before do_image
do_copydtb () {
  cp ${DEPLOY_DIR_IMAGE}/zImage-socfpga_cyclone5_de0_sockit.dtb ${DEPLOY_DIR_IMAGE}/socfpga.dtb
}

addtask sudoers after do_rootfs before do_image
#    echo "user1 ALL=(ALL) ALL" > ${IMAGE_ROOTFS}${sysconfdir}/sudoers.d/001_first
do_sudoers () {
    echo "Testing"
    echo "Does ${HOME}/.ssh/id_rsa.pub exist?"
    if [ -f ${HOME}/.ssh/id_rsa.pub ]; then
      echo "YES IT DOES $(date)"
      install -d ${IMAGE_ROOTFS}/home/gumstix/.ssh -m 0700
      cat ${HOME}/.ssh/id_rsa.pub >> ${IMAGE_ROOTFS}/home/gumstix/.ssh/authorized_keys
      chmod 0600 ${IMAGE_ROOTFS}/home/gumstix/.ssh/authorized_keys
      echo "%sudo ALL=(ALL) ALL" >> ${IMAGE_ROOTFS}${sysconfdir}/sudoers
    fi
    glib-compile-schemas ${IMAGE_ROOTFS}/usr/share/glib-2.0/schemas/
}

IMAGE_POSTPROCESS_COMMAND += "do_sdbuild"
do_sdbuild() {
  echo "Deploy dir = ${DEPLOY_DIR_IMAGE}"
  echo "work dir = ${WORKDIR}"
  files="${DEPLOY_DIR_IMAGE}/*.wic"
  for f in $files; do
    if [ -z "$newest" ]; then
      newest=$f
    fi
    if [[ $f -nt $newest ]]; then
      newest=$f
    fi
  done
  echo "Temp name2 = $newest"
  bmaptool create $newest > ${DEPLOY_DIR_IMAGE}/${PN}-${MACHINE}.bmap
  echo "bmaptool copy --bmap ${WORKDIR}/${PN}-${MACHINE}.bmap $newest ${DISK_DEVICE}"
}

# Gumstix machines individually RDEPEND on the firware they need but we repeat
# it here as we might want to use the same image on multiple different machines.
FIRMWARE_INSTALL = " \
  u-boot \
  linux-firmware-sd8686 \
  linux-firmware-sd8787 \
  linux-firmware-wl18xx \
"

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
  xserver-nodm-init \
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
  ${FIRMWARE_INSTALL} \
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

IMAGE_INSTALL += " \
 aws-progs \
 aws-progs-keygen \
"

# Create a generic 'gumstix' user account, part of the gumstix group,
# using '/bin/sh' and with a home directory '/home/gumstix' (see
# /etc/default/useradd).  We set the password to 'gumstix' and add them
# to the 'sudo' group.
inherit extrausers
EXTRA_USERS_PARAMS = " \
    usermod -P root root; \
    useradd -P gumstix -G sudo gumstix; \
"


