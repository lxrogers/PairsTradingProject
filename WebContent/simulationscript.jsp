<script>

var ctx = canvas.getContext('2d');
var beginBar = {
		x: 10,
		color: '#4ABA1E',
};
var endBar = {
		x:990,
		color: '#F5B042',
};
var bar = null;
var down = false;
Initialize();


function Initialize() {
	canvas.addEventListener('mousemove', MouseMove, false);
	canvas.addEventListener('mousedown', MouseDown, false);
	canvas.addEventListener('mouseup', MouseUp, false);
	setInterval(Interval, 20);
	bar = null;
}

function Interval() {
	ctx.clearRect(0, 0, window.innerWidth, window.innerHeight);
	ctx.fillStyle = '#FFFFFF'; // set canvas background color
	//ctx.fillRect(0, 0, window.innerWidth, window.innerHeight);
	drawBar(beginBar);
	drawBar(endBar);
	drawRect();
	
}
function drawBar(b) {
	// Filled triangle
	ctx.globalAlpha = .7;
    ctx.beginPath();
    ctx.moveTo(b.x,0);
    ctx.lineTo(b.x, 1000);
    ctx.lineWidth = 5;
    ctx.strokeStyle = b.color;
    ctx.closePath();
    ctx.stroke();
    ctx.globalAlpha =1;
}
function drawRect() {
	ctx.globalAlpha=0.1;
	ctx.fillStyle = '#000000';
	ctx.fillRect(beginBar.x + 5,0,endBar.x - beginBar.x - 10,700);
	console.log(endBar.x);
    ctx.globalAlpha = 1;
}
function testBarClick(b, x) {
	if (x > b.x - 25 && x < b.x + 25) return true;
	return false;
}
function MouseMove(e) {
	if (down && bar != null) {
		bar.x = e.layerX;	
	}
	
}
function MouseDown(e) {
	down = true;
	if (testBarClick(beginBar, e.layerX)) {
		bar= beginBar;
	}
	else if (testBarClick(endBar, e.layerX)) {
		bar = endBar;
	}
}
function MouseUp(e) {
	down = false;
	bar = null;
}
</script>