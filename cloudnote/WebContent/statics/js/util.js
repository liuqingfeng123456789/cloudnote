
/**
 * 判断参数是否为空
 * @param str
 * @returns {Boolean}
 */
function isEmpty(str){
	if (str == null || str.trim() == "") {
		return true;
	}
	return false;
}

/**
 * 判断参数是否不为空
 * @param str
 * @returns {Boolean}
 */
function isNotEmpty(str){
	if (str == null || str.trim() == "") {
		return false;
	}
	return true;
}