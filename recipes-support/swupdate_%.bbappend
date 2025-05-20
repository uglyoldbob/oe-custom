FILESEXTRAPATHS:prepend := "${TOPDIR}:${THISDIR}/swupdate:"

addtask add_encryption_data after do_compile before do_install

INSANE_SKIP:${PN} += "buildpaths"

SRC_URI += "file://tempdefconfig \
	file://conf/signing/public.pem \
	file://conf/keys.conf \
	file://swupdate.cfg \
	file://09-swupdate-args \
"

S = "${WORKDIR}/git"

DEPENDS += " libarchive curl"
RDEPENDS:${PN} += " bash"

inherit swupdate-lib

python do_add_encryption_data() {
    workdir = d.getVar('UNPACKDIR', True)
    s = d.getVar('S', True)
    with open(os.path.join(s, "encryption_key"), 'w+') as g:
        k, ivt = swupdate_extract_keys(os.path.join(workdir, "conf/keys.conf"))
        g.write(k)
        g.write(" ")
        g.write(ivt)
}

do_configure () {
    rm -f ${UNPACKDIR}/defconfig
    cp ${UNPACKDIR}/tempdefconfig ${UNPACKDIR}/defconfig
    cat ${UNPACKDIR}/tempdefconfig >> ${UNPACKDIR}/.config
    cp ${UNPACKDIR}/.config ${S}/.config
    (cd ${S} && cml1_do_configure)
}

do_install:append() {
	install -d ${D}/etc/
	install -d ${D}/etc/swupdate
	install -m 0600 ${UNPACKDIR}/swupdate.cfg ${D}/etc/swupdate.cfg
	install -m 0600 ${UNPACKDIR}/conf/signing/public.pem ${D}/etc/swupdatepub.key
	install -m 0600 ${S}/encryption_key ${D}/etc/swupdate/encryption
	install -d ${D}/usr/bin
	echo "${SWU_MACHINE_NAME} ${SWU_MACHINE_VERSION}" > ${D}/etc/hwrevision
	install -m 0644 ${UNPACKDIR}/09-swupdate-args ${D}${libdir}/swupdate/conf.d/
	sed -i "s#@MACHINE@#${MACHINE}#g" ${D}${libdir}/swupdate/conf.d/09-swupdate-args
}

