# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=8ed1a118f474eea5e159b560c339329b"

SRC_URI = "git://github.com/cu-ecen-aeld/assignments-3-and-later-biplavpoudel.git;branch=main;protocol=https"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "c9783c6f87f5fe1a972b7bf4f6b8ff266f3ee176"

S = "${WORKDIR}/git/aesd-char-driver"

# module.bbclass defines do_compile(), do_install(), and kernel build integration logic.
# during do_compile, it runs: `make -C ${STAGING_KERNEL_DIR} M=${S} modules`
# while, update-rc.d runs post-install steps that create start/stop symlinks in runlevel directories for aesdchar_init
inherit module update-rc.d

FILES:${PN} += "${sysconfdir}/init.d/aesdchar"
FILES:${PN} += "${bindir}/aesdchar_load"
FILES:${PN} += "${bindir}/aesdchar_unload"

#EXTRA_OEMAKE line is what passes the correct kernel path to override the Makefile's default KERNELDIR ?= /lib/modules/$(shell uname -r)/build.
EXTRA_OEMAKE += "-C ${STAGING_KERNEL_DIR} M=${S} EXTRA_CFLAGS=-I${S}/include"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "aesdchar"
# start priority = 90 (low); stop priority = 10 (high, automatically derived)
INITSCRIPT_PARAMS = "defaults 90"

do_install () {
	module_do_install

	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/aesdchar_init ${D}${sysconfdir}/init.d/aesdchar

	install -d ${D}${bindir}
    install -m 0755 ${S}/aesdchar_load ${D}${bindir}/
    install -m 0755 ${S}/aesdchar_unload ${D}${bindir}/
}
