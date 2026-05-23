# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-biplavpoudel.git;protocol=https;branch=master \
           file://misc-modules-init.sh \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "ba0562a42dd438e140ba1c701fb88963fe5cbcbd"

S = "${WORKDIR}/git/misc-modules"

inherit module update-rc.d

# module.bbclass only auto-ships the .ko files.
FILES:${PN} += "${sysconfdir}/init.d/misc-modules"
FILES:${PN} += "${bindir}/module_load"
FILES:${PN} += "${bindir}/module_unload"

#EXTRA_OEMAKE line is what passes the correct kernel path to override the Makefile's default KERNELDIR ?= /lib/modules/$(shell uname -r)/build.
EXTRA_OEMAKE += " -C ${STAGING_KERNEL_DIR} M=${S} EXTRA_CFLAGS=-I${S}/../include"

# Init script config
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "misc-modules"
INITSCRIPT_PARAMS = "defaults 90"

do_install () {
	module_do_install

	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/misc-modules-init.sh ${D}${sysconfdir}/init.d/misc-modules

	install -d ${D}${bindir}
    install -m 0755 ${S}/module_load  ${D}${bindir}/
    install -m 0755 ${S}/module_unload  ${D}${bindir}/
}
