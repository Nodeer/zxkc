/**
 * base64 encode / decode
 * @location    http://www.webtoolkit.info/
 *
 */
 
var Base64 = (function() {
    // Private property
    var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
 
    // Private method for UTF-8 encoding
    function utf8Encode(string) {
        string = string.replace(/\\r\\n/g,"\\n");
        var utftext = "";
        for (var n = 0; n < string.length; n++) {
            var c = string.charCodeAt(n);
            if (c < 128) {
                utftext += String.fromCharCode(c);
            }
            else if((c > 127) && (c < 2048)) {
                utftext += String.fromCharCode((c >> 6) | 192);
                utftext += String.fromCharCode((c & 63) | 128);
            }
            else {
                utftext += String.fromCharCode((c >> 12) | 224);
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                utftext += String.fromCharCode((c & 63) | 128);
            }
        }
        return utftext;
    }
 
    // Public method for encoding
    return {
        encode : (typeof btoa == 'function') ? function(input) {
            return btoa(utf8Encode(input));
        } : function (input) {
            var output = "";
            var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
            var i = 0;
            input = utf8Encode(input);
            while (i < input.length) {
                chr1 = input.charCodeAt(i++);
                chr2 = input.charCodeAt(i++);
                chr3 = input.charCodeAt(i++);
                enc1 = chr1 >> 2;
                enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                enc4 = chr3 & 63;
                if (isNaN(chr2)) {
                    enc3 = enc4 = 64;
                } else if (isNaN(chr3)) {
                    enc4 = 64;
                }
                output = output +
                keyStr.charAt(enc1) + keyStr.charAt(enc2) +
                keyStr.charAt(enc3) + keyStr.charAt(enc4);
            }
            return output;
        }
    };
})();
 
Ext.override(Ext.grid.GridPanel, {
    getExcelXml: function(includeHidden, config) {
        var worksheet = this.createWorksheet(includeHidden, config);
        var totalWidth = this.getColumnModel().getTotalWidth(includeHidden);
        return worksheet.xml;
    },
 
    createWorksheet: function(includeHidden, config) {
        // Calculate cell data types and extra class names which affect formatting
        var cellType = [];
        var cellTypeClass = [];
        var cm = this.getColumnModel();
        var totalWidthInPixels = 0;
        var headerXml = '';
        var visibleColumnCountReduction = 0;
        var colCount = cm.getColumnCount();
        var innerstore = null;
        if (config) {
            innerstore = config;
        } else {
            innerstore = this.store;
        }
        
        for (var i = 0; i < colCount; i++) {
            if ((cm.getDataIndex(i) != '')
                && (includeHidden || !cm.isHidden(i))) {
                var w = cm.getColumnWidth(i)
                totalWidthInPixels += w;
                if (cm.getColumnHeader(i) === ""){
                    cellType.push("None");
                    cellTypeClass.push("");
                    ++visibleColumnCountReduction;
                }
                else
                {
                	if(cm.getColumnHeader(i)!='操作'){
                		headerXml += cm.getColumnHeader(i) + ',' ;
                	}
                    
                }
            }
        }
        var visibleColumnCount = cellType.length - visibleColumnCountReduction;
 
        var result = {
            height: 9000,
            width: Math.floor(totalWidthInPixels * 30) + 50
        };
 
        // Generate worksheet header details.
        var t = '';
 
        // Generate the data rows from the data in the Store
        for (var i = 0, it = innerstore.data.items, l = it.length; i < l; i++) {
            r = it[i].data;
            var k = 0;
            for (var j = 0; j < colCount; j++) {
                if ((cm.getDataIndex(j) != '')&& (includeHidden || !cm.isHidden(j))&&cm.getColumnHeader(j)!='操作') {
            		var v = r[cm.getDataIndex(j)];
                    if (cellType[k] !== "None") {
                        if (cellType[k] == 'DateTime') {
                            t += v.format('Y-m-d');
                        } else {
                            t += v;
                        }
                    }
                    k++;
                    t+=",";
                }
                
            }
            t+=";";
        }
 
        result.xml = headerXml+";"+t ;
        return result;
    }
});

function exportExcel(gridPanel, excelName) {
		//showProcessMsg();
        var tmpStore = gridPanel.getStore();
        //以下处理分页grid数据导出的问题，从服务器中获取所有数据，需要考虑性能
        var tmpParam = tmpStore.baseParams;
        var tmpAllStore = new Ext.data.GroupingStore({//重新定义一个数据源
            proxy: tmpStore.proxy,
            reader: tmpStore.reader,
            baseParams:tmpParam
        });
        tmpAllStore.setBaseParam('start',0);
        tmpAllStore.setBaseParam('limit',999999999);
        var vExportContent='';
        tmpAllStore.on('load', function (store) {
               tmpExportContent = gridPanel.getExcelXml(false, store); //此方法用到了一中的扩展
               if (Ext.isIE || Ext.isSafari || Ext.isSafari2 || Ext.isSafari3) {//在这几种浏览器中才需要，IE8测试不能直接下载了
                              if (!Ext.fly('frmDummy')) {
                                 var frm = document.createElement('form');
                                 frm.id = 'frmDummy';
                                 frm.name = id;
                                 frm.className = 'x-hidden';
                                 document.body.appendChild(frm);
                             }
                              Ext.Ajax.request({
                                //将生成的xml发送到服务器端,需特别注意这个页面的地址
                                url: '${ctxPath}/cxtj/kccx_exportExcel.shtml',                      
                                method: 'POST',
                                form: Ext.fly('frmDummy'),
                                isUpload: true,
                                params: { exportContent: vExportContent, excelName: excelName }
                            });
                            hideProcessMsg();
            } else {
                document.location = 'data:application/vnd.ms-excel;base64,' + Base64.encode(tmpExportContent);
            }
       });
        tmpAllStore.load();

}