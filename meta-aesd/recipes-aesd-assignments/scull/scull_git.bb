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
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-biplavpoudel.git;protocol=https;branch=master \
           file://0001-Fix-Makefile-for-scull-misc-modules-build.patch \
           file://scull-init.sh \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "ba0562a42dd438e140ba1c701fb88963fe5cbcbd"

S = "${WORKDIR}/git"

inherit module update-rc.d

FILES:${PN} += "${sysconfdir}/init.d/scull"
FILES:${PN} += "${bindir}/scull_load"
FILES:${PN} += "${bindir}/scull_unload"

EXTRA_OEMAKE += " -C ${STAGING_KERNEL_DIR} M=${S}/scull EXTRA_CFLAGS=-I${S}/include"

# Init script config
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "scull"
INITSCRIPT_PARAMS = "defaults 90"

#Linker flags
TARGET_LDFLAGS += "-pthread -lrt"

do_install () {
	module_do_install
	install -d ${D}${bindir}
	install -d ${D}${sysconfdir}/init.d

	install -m 0755 ${WORKDIR}/scull-init.sh ${D}${sysconfdir}/init.d/scull

    	install -m 0755 ${S}/scull/scull_load ${D}${bindir}/scull_load
    	install -m 0755 ${S}/scull/scull_unload ${D}${bindir}/scull_unload
}
