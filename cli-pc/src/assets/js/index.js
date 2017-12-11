export const urlJsonList = obj =>{
    return Object.keys(obj)
    .map(function(k) {
        return encodeURIComponent(k) + '=' + encodeURIComponent(obj[k])
    })
    .join('&')
}