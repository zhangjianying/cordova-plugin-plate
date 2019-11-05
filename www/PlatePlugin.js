var exec = require('cordova/exec');
var platePlugin = {
	/**
	 *
	 * @param {*} url   服务器地址
	 * @param {*} autoSubmitScore  自动返回置信率. 高于这个置信率则自动返回结果 一般是0.995
	 * @param {*} success
	 * @param {*} error
	 */
	show: function (url,autoSubmitScore,success, error) {
		var params={
			url:url,
			autoSubmitScore:autoSubmitScore
		}
		exec(success, error, "PlatePlugin", "show", [params]);
	}
}
module.exports = platePlugin;