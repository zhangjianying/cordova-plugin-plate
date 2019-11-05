# cordova-plugin-plate
cordova车牌识别插件


* 插件不包含识别模型,插件本身只是做拍摄,以及初步图形处理(判断拍摄图像是否模糊,是否包含车牌等)
* 插件通过提交图片到配套后台服务进行车牌识别
* 已测试适配android 4.4 ~ android 9

## 安装:
```
 cordova plugin add https://github.com/zhangjianying/cordova-plugin-plate.git
```

## 使用:
```javascript
        platePlugin.show("http://XXXXXXXXX",0.995,function(successObj){
          var str='code:'+successObj.code+'\n'+
                  'desc:'+successObj.desc+'\n'+
                  'plate:'+successObj.plate+'\n'+
                  'colorDesc:'+successObj.colorDesc+'\n'+
                  'score:'+successObj.score;
          alert(str)
        },function(failObj){

        });

```

## 说明

platePlugin.show([url],[autoSubmitScore],successFn,failFn);

url -- 车牌识别服务器地址

autoSubmitScore -- 自动确认置信率, 当车牌识别置信率超过这个设定值,插件则自动确认.否则 需要人工在界面确认



当置信率不高的时候,会给出3个最佳识别结果,供用户手动点选,如果置信率达到设定就会自动选择


## 预览

![预览1](https://github.com/zhangjianying/cordova-plugin-plate/raw/master/readme/1.jpg)
![预览2](https://github.com/zhangjianying/cordova-plugin-plate/raw/master/readme/2.jpg)