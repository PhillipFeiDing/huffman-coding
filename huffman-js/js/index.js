const tableEntires = 34
let currentMode = 'Encode'

$('#clear-button').on('click', () => {
    $('#left-textarea').val('')
})

$('#toggle-button').on('click', () => {
    if (currentMode == 'Encode') {
        currentMode = 'Decode'
    } else {
        currentMode = 'Encode'
    }
    $('#mode-select').html(currentMode)
    if (currentMode === 'Encode') {
        $('#mode-select').removeClass('decode-mode')
        $('#mode-select').addClass('encode-mode')
        $('#prompt-left').html('Original Text')
        $('#prompt-right').html('Encrypted Bits')
    } else {
        $('#mode-select').removeClass('encode-mode')
        $('#mode-select').addClass('decode-mode')
        $('#prompt-left').html('Encrypted Bits')
        $('#prompt-right').html('Original Text')
    }
    const toReplace = $('#right-textarea').val()
    if (toReplace.includes('ERROR')) {
        $('#left-textarea').val('')
    } else {
        $('#left-textarea').val(toReplace)
    }
    $('#right-textarea').val('')
})

$('#convert-button').on('click', () => {
    clearTables()
    if (currentMode === 'Encode') {
        const message = $('#left-textarea').val()
        if (message.length == 0) {
            $('#right-textarea').val('ERROR: Empty Message')
            return
        }
        const encoder = new Encoder()
        encoder.encode(message)
        const result = {}
        result['scheme'] = encoder.getScheme()
        result['body'] = encoder.getEncoded()
        const freqChart = encoder.getFreqChart()
        fillFreqChart(freqChart)
        fillEncodeScheme(result['scheme'])
        $('#right-textarea').val(JSON.stringify(result))
    } else {
        const objStr = $('#left-textarea').val()
        try {
            const obj = JSON.parse(objStr)
            const scheme = obj.scheme
            Object.setPrototypeOf(scheme, HashMap.prototype)
            fillEncodeScheme(scheme)
            const body = obj.body
            const decoder = new Decoder(scheme)
            const message = decoder.decode(body)
            $('#right-textarea').val(message)
        } catch (e) {
            $('#right-textarea').val('ERROR: Malformed JSON or Invalid Scheme')
        }
    }
})

$('#copy-button').on('click', () => {
    copyToClipboard(document.getElementById('right-textarea'))
})

function clearTables() {
    for (let idx = 0; idx < tableEntires; idx++) {
        $('#freq-chart-char-' + idx).html('&nbsp;')
        $('#freq-chart-freq-' + idx).html('&nbsp;')
        $('#encode-scheme-char-' + idx).html('&nbsp;')
        $('#encode-scheme-scheme-' + idx).html('&nbsp;')
    }
}

function fillFreqChart(freqChart) {
    const keys = freqChart.keySet()
    for (let i = 0; i < keys.length && i < tableEntires; i++) {
        $('#freq-chart-char-' + i).html(keys[i])
        $('#freq-chart-freq-' + i).html(freqChart.get(keys[i]))
    }
}

function fillEncodeScheme(encodeScheme) {
    const keys = encodeScheme.keySet()
    for (let i = 0; i < keys.length && i < tableEntires; i++) {
        $('#encode-scheme-char-' + i).html(keys[i])
        $('#encode-scheme-scheme-' + i).html(encodeScheme.get(keys[i]))
    }
}

function copyToClipboard(elem) {
    // create hidden text element, if it doesn't already exist
  var targetId = "_hiddenCopyText_";
  var isInput = elem.tagName === "INPUT" || elem.tagName === "TEXTAREA";
  var origSelectionStart, origSelectionEnd;
  if (isInput) {
      // can just use the original source element for the selection and copy
      target = elem;
      origSelectionStart = elem.selectionStart;
      origSelectionEnd = elem.selectionEnd;
  } else {
      // must use a temporary form element for the selection and copy
      target = document.getElementById(targetId);
      if (!target) {
          var target = document.createElement("textarea");
          target.style.position = "absolute";
          target.style.left = "-9999px";
          target.style.top = "0";
          target.id = targetId;
          document.body.appendChild(target);
      }
      target.textContent = elem.textContent;
  }
  // select the content
  var currentFocus = document.activeElement;
  target.focus();
  target.setSelectionRange(0, target.value.length);
  
  // copy the selection
  var succeed;
  try {
        succeed = document.execCommand("copy");
  } catch(e) {
      succeed = false;
  }
  // restore original focus
  if (currentFocus && typeof currentFocus.focus === "function") {
      currentFocus.focus();
  }
  
  if (isInput) {
      // restore prior selection
      elem.setSelectionRange(origSelectionStart, origSelectionEnd);
  } else {
      // clear temporary content
      target.textContent = "";
  }
  return succeed;
}