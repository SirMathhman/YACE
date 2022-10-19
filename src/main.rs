fn main() {
    println!("Hello, world!");
}

#[cfg(test)]
mod tests {
    use std::fs::File;
    use std::path::Path;

    #[test]
    fn test() {
        let path = Path::new("Index.mgs");
        assert_eq!(true, File::open(path).is_err());
    }
}