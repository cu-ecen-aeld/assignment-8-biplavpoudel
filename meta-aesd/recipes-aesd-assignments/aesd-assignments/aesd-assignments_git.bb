# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# Setting this  with the path to our assignments 3 repo, using ssh protocol and ssh-agent for passwordless access
SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-biplavpoudel;protocol=ssh;branch=main"

PV = "1.0+git${SRCPV}"
# set to reference a specific commit hash in our assignment repo
SRCREV = "e1a7b68161247701e5e0408486db68ba755f644c"

# This sets our staging directory based on WORKDIR, where WORKDIR is defined at 
# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-WORKDIR
# We reference the "server" directory here to build from the "server" directory in our assignments repo
S = "${WORKDIR}/git/server"

# Adding the aesdsocket application and any other files we need to install
# See https://git.yoctoproject.org/poky/plain/meta/conf/bitbake.conf?h=kirkstone
FILES:${PN} += "${bindir}/aesdsocket ${bindir}/aesdsocket-start-stop"
FILES:${PN}-dev += "${includedir}/queue.h"

TARGET_LDFLAGS += "-pthread -lrt"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
	# TODO: Install your binaries/scripts here.
	# Be sure to install the target directory with install -d first
	# Yocto variables ${D} and ${S} are useful here, which you can read about at 
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
	# and
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
	# See example at https://github.com/cu-ecen-aeld/ecen5013-yocto/blob/ecen5013-hello-world/meta-ecen5013/recipes-ecen5013/ecen5013-hello-world/ecen5013-hello-world_git.bb

	install -d ${D}${bindir}
	install -m 0755 ${S}/aesdsocket ${D}${bindir}/aesdsocket
}
