DESCRIPTION = "glx driver for nvidia"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://nvidia_icd.json"

INSANE_SKIP:${PN} = "ldflags"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
SOLIBS = ".so"
FILES_SOLIBSDEV = ""

do_install:append() {
    install -d ${D}/etc/xdg/icd.d
    install -m 0644 ${WORKDIR}/sources-unpack/nvidia_icd.json ${D}/etc/xdg/icd.d/nvidia_icd.json
    install -d ${D}${libdir}
    install -m 0777 ${S}/libGLX_nvidia.so ${D}${libdir}/libGLX_nvidia.so
}

FILES:${PN} += "/etc/xdg/icd.d/nvidia_icd.json"

