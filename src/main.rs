fn main() {
    println!("Hello, world!");
}

#[cfg(test)]
mod tests {
    use std::fs::File;
    use std::path::Path;

    #[test]
    fn does_not_generate() {
        let path = Path::new("Index.mgs");
        assert_eq!(true, File::open(path).is_err());
    }

    #[test]
    fn generates() {
        let path = Path::new("Index.mgs");
        File::create(path).unwrap();
        assert_eq!(false, File::open(path).is_err())
    }
}