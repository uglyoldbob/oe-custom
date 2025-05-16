#!/bin/bash
CURRENT_ROOT=$(swupdate -g)
CURRENT_PART="${CURRENT_ROOT: -1}"
if [ $CURRENT_PART = "2" ]; then
	UPDATE_PART="3";
else
	UPDATE_PART="2";
fi
UPDATE_ROOT=${CURRENT_ROOT%?}${UPDATE_PART}
ln -sf ${UPDATE_ROOT} /dev/update
