const app = require('express')();
const multer = require('multer');
const mkdirp = require('mkdirp');

app.use(require('morgan')('dev'));

const storage = multer.diskStorage({
  destination: function(req, file, cb) {
    mkdirp(`./upload/${req.body.folder}`, function(err) {
      if (err) {
        console.log(err);
      } else {
        cb(null, `./upload/${req.body.folder}`);
      }
    });
  },
  filename: function(req, file, cb) {
    console.log(file);
    cb(null, file.originalname);
  }
});

const upload = multer({ storage: storage });

// app.get('/', (req, res) => res.send('Hello World'));

app.post('/upload', function(req, res) {
  upload.array('photos')(req, res, function(err) {
    if (err) {
      res.json({ result: false });
    } else {
      res.json({ result: true });
    }
  });
});

app.listen(3000, () => console.log('Server is running...'));
