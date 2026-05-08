#!/bin/sh

# Script to load/unload faulty driver and hello module from misc-module.

case "$1" in
    start)
        echo "Loading misc-modules"
        faulty_load || exit 1
        modprobe hello || exit 1        #Loading hello module using modprobe
        ;;

    stop)
        echo "Unloading misc-modules"
        faulty_unload
        rmmod hello 2>/dev/null
        ;;

    *)
        echo "Usuage: $0 {start | stop}"
        exit 1

esac

exit 0