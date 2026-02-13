require recipes-kernel/linux/linux-yocto.inc

COMPATIBLE_MACHINE = "overo"

# Pull in the devicetree files into the rootfs
RDEPENDS:kernel-base += "kernel-devicetree"

KERNEL_DEVICETREE_overo = "omap3-overo-chestnut43.dtb"

# Auto-load a serial+ethernet connection on a USB OTG/peripheral
# port if available
KERNEL_MODULE_AUTOLOAD += "g_cdc"

LINUX_VERSION = "4.13"
LINUX_VERSION_EXTENSION = "-custom"

BOOT_SPLASH = "TinyScreenBootup224.ppm"

FILESEXTRAPATHS:prepend := "${THISDIR}/linux-gumstix-4.13:"

S = "${WORKDIR}/git"

do_compile:prepend() {
  cp -fv ${WORKDIR}/${BOOT_SPLASH} ${WORKDIR}/git/drivers/video/logo/logo_linux_clut224.ppm
}

# v4.13.y = 5f1d25a9a8c999ddb1a18a6b167f1284a46e4dd1
SRCREV = "5f1d25a9a8c999ddb1a18a6b167f1284a46e4dd1"

SRC_URI = " \   
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;nocheckout=1;branch=linux-4.13.y \
    file://defconfig \
    file://${BOOT_SPLASH} \
"
