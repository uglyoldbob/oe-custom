DESCRIPTION = "U-Boot - the Universal Boot Loader"
HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
SECTION = "bootloaders"
PRIORITY = "optional"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=4c6cde5df68eff615d36789dc18edd3b"
PROVIDES = "virtual/bootloader"

S = "${WORKDIR}/git"
COMPATIBLE_MACHINE = "verdex"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

DEPENDS = "libgcc u-boot-verdex-mkimage"

PR = "r7"

SRCREV = "ccbbb06d53cbbb6b37cd41f0a11ae6d706923409"
SRC_URI = "git://github.com/uglyoldbob/verdex-uboot.git \
	file://gumstix-factory.script.source \
	file://0001-Add-device-tree-support-for-verdex.patch \
"

do_configure () {
	export CROSS_COMPILE="${TARGET_PREFIX}"
	make gumstix_config
}

do_compile () {
	export CROSS_COMPILE=" "
	make tools	
	export CROSS_COMPILE="${TARGET_PREFIX}"
	export HOSTCC="${BUILD_CC}"
	unset LDFLAGS
	unset CFLAGS
	unset CPPFLAGS
	make CC="$CC" GUMSTIX_400MHZ=y GUMSTIX_600MHZ=n u-boot.bin 
	uboot-verdex-mkimage -A arm -O linux -T script -C none -a 0 -e 0 -n gumstix-factory.script -d ${WORKDIR}/gumstix-factory.script.source gumstix-factory.script
}

do_install () {
	if [ -e ${WORKDIR}/fw_env.config ] ; then
		install -d ${D}$base_sbindir}
		install -d ${D}${sysconfdir}
		install -m 644 ${WORKDIR}/fw_env.config ${D}${sysconfdir}/fw_env.config
		install -m 755 ${S}/tools/env/fw_printenv ${D}${base_sbindir}/fw_printenv
		install -m 755 ${S}/tools/env/fw_printenv ${D}${base_sbindir}/fw_setenv
	fi

	install -d ${D}${sbindir}
	install -m 0755 ${S}/tools/env/fw_printenv ${D}${sbindir}
	ln -s /usr/sbin/fw_printenv ${D}${sbindir}/fw_setenv
	install -d ${D}${sysconfdir}
	install -m 0644 ${S}/tools/env/fw_env.config ${D}${sysconfdir}
}

do_deploy () {
	install -d ${DEPLOY_DIR_IMAGE}
	install ${S}/u-boot.bin ${DEPLOY_DIR_IMAGE}/u-boot.bin
	install ${S}/gumstix-factory.script ${DEPLOY_DIR_IMAGE}/gumstix-factory.script
}

do_deploy[dirs] = "${S}"
addtask deploy before do_build after do_compile

inherit nopackages
