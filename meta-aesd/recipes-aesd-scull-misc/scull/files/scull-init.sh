#!/bin/sh

# Script to load/unload scull driver, and faulty driver and hello module from misc-module.

case "$1" in
    start)
        echo "Loading scull driver"
        scull_load || exit 1
        ;;

    stop)
        echo "Unloading scull driver"
        scull_unload
        ;;

    *)
        echo "Usuage: $0 {start | stop}"
        exit 1

esac

exit 0