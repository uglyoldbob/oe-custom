require recipes-kernel/linux/linux-yocto.inc

COMPATIBLE_MACHINE = "de0nano"

# Pull in the devicetree files into the rootfs
RDEPENDS:kernel-base += "kernel-devicetree"

KERNEL_DEVICETREE_de0nano = "socfpga_cyclone5_de0_sockit.dtb \
 de0Test.dtb"

LINUX_VERSION = "4.13"
LINUX_VERSION_EXTENSION = "-custom"

BOOT_SPLASH = "TinyScreenBootup224.ppm"

FILESEXTRAPATHS:prepend := "${THISDIR}/linux-cyclone-4.13:"

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
    file://0001-Implement-an-ili9341-framebuffer-driver.patch \
    file://0002-Add-memory-map-joystick-support.patch \
"
