DESCRIPTION = "A 32M flash image for Gumstix verdex boards."
LICENSE = "MIT"

require verdex-flash-image.inc

#max size for 32m flash image
UBOOT_MAXSIZE = "256 * 2^10"
KERNEL_MAXSIZE = "4 * 2^20"
DT_MAXSIZE = "32 * 2^10"
FLASH_SIZE = "32 * 2^20"
ROOTFS_MAXSIZE = "${FLASH_SIZE} - ${KERNEL_MAXSIZE} - ${DT_MAXSIZE} - ${UBOOT_MAXSIZE}"

SIMULATOR_ARGS += " -pflash ${DEPLOY_DIR_IMAGE}/${IMAGE_BASENAME}.flash"


