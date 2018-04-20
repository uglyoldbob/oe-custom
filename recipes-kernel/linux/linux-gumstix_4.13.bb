KERNEL_IMAGETYPE_verdex = "uImage"

require recipes-kernel/linux/linux-yocto.inc

COMPATIBLE_MACHINE = "overo|verdex"

# Pull in the devicetree files into the rootfs
RDEPENDS_kernel-base += "kernel-devicetree"

RDEPENDS_kernel-base_verdex = ""

DEPENDS_verdex += "u-boot-mkimage-native"

KERNEL_EXTRA_ARGS_verdex += "LOADADDR=0xa0008000"

KERNEL_DEVICETREE_overo = "omap3-overo-chestnut43.dtb"

KERNEL_DEVICETREE_verdex = "verdex.dtb"

# Auto-load a serial+ethernet connection on a USB OTG/peripheral
# port if available
KERNEL_MODULE_AUTOLOAD += "g_cdc"

LINUX_VERSION = "4.13"
LINUX_VERSION_EXTENSION = "-custom"

BOOT_SPLASH ?= "logo_linux_clut224-generic.ppm"

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-gumstix-4.13:"

S = "${WORKDIR}/git"

# v4.13.y = 5f1d25a9a8c999ddb1a18a6b167f1284a46e4dd1
SRCREV = "5f1d25a9a8c999ddb1a18a6b167f1284a46e4dd1"

SRC_URI = " \   
    git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;nocheckout=1;branch=linux-4.13.y \
    file://defconfig \
    file://${BOOT_SPLASH} \
"

SRC_URI_verdex += " \
    file://0001-Add-gumstix-verdex-devicetree-bare-minimum.patch \
"
